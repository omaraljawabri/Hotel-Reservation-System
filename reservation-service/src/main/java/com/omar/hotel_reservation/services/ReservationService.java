package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.clients.HotelClient;
import com.omar.hotel_reservation.clients.RoomClient;
import com.omar.hotel_reservation.clients.UserClient;
import com.omar.hotel_reservation.dtos.request.ReservationPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.*;
import com.omar.hotel_reservation.entities.Reservation;
import com.omar.hotel_reservation.entities.Status;
import com.omar.hotel_reservation.exceptions.BusinessException;
import com.omar.hotel_reservation.exceptions.EntityDoesntBelongException;
import com.omar.hotel_reservation.exceptions.UserNotFoundException;
import com.omar.hotel_reservation.kafka.CancelReservationProducer;
import com.omar.hotel_reservation.kafka.ConfirmReservationProducer;
import com.omar.hotel_reservation.kafka.ReservationKafka;
import com.omar.hotel_reservation.mappers.ReservationMapper;
import com.omar.hotel_reservation.repositories.ReservationRepository;
import com.omar.hotel_reservation.utils.HotelStatus;
import com.omar.hotel_reservation.utils.RoomStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final UserClient userClient;
    private final HotelClient hotelClient;
    private final RoomClient roomClient;
    private final ReservationMapper mapper;
    private final ConfirmReservationProducer confirmReservationProducer;
    private final CancelReservationProducer cancelReservationProducer;
    private final SystemTokenService systemTokenService;

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        UserResponseDTO userResponseDTO = validateUser(reservationRequestDTO.userId());
        HotelResponseDTO hotelResponseDTO = validateHotel(reservationRequestDTO.hotelId());
        RoomResponseDTO roomResponseDTO = validateRoom(reservationRequestDTO.roomId());
        if (!Objects.equals(roomResponseDTO.hotelId(), hotelResponseDTO.id())){
            throw new EntityDoesntBelongException(String.format("Room with id: %d doesn't belong to Hotel with id: %d", roomResponseDTO.id(), hotelResponseDTO.id()));
        }
        if (roomResponseDTO.status() != RoomStatus.AVAILABLE || hotelResponseDTO.status() != HotelStatus.ACTIVE){
            throw new BusinessException(String.format("Room with id: %d or Hotel with id: %d are not available", roomResponseDTO.id(), hotelResponseDTO.id()));
        }
        Reservation reservation = mapper.toReservation(reservationRequestDTO);
        reservation.setBookingDate(LocalDateTime.now());
        reservation.setStatus(Status.PENDING);
        confirmReservationProducer.sendConfirmReservationTopic(new ReservationKafka(
                userResponseDTO.firstName(),
                userResponseDTO.lastName(),
                userResponseDTO.email(),
                roomResponseDTO.roomNumber(),
                hotelResponseDTO.name(),
                hotelResponseDTO.country(),
                hotelResponseDTO.state(),
                hotelResponseDTO.city(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        ));
        updateRoom(roomResponseDTO, RoomStatus.RESERVED);
        Reservation reservationSaved = repository.save(reservation);
        return mapper.toReservationResponse(reservationSaved, userResponseDTO, hotelResponseDTO, roomResponseDTO);
    }

    public List<UserReservationResponseDTO> findByUserId(Long userId) {
        UserResponseDTO userResponseDTO = validateUser(userId);
        List<Reservation> reservations = repository.findAllByUserId(userResponseDTO.id());
        return forEachReservation(reservations);
    }

    public List<UserReservationResponseDTO> findConfirmedByUserId(Long userId) {
        UserResponseDTO userResponseDTO = validateUser(userId);
        List<Reservation> reservations = repository.findAllByUserIdAndStatus(userResponseDTO.id(), Status.CONFIRMED);
        return forEachReservation(reservations);
    }

    @Transactional
    public void cancelReservation(Long id, Long userId) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with id: %d, not found", id)));
        UserResponseDTO userResponseDTO = validateUser(userId);
        HotelResponseDTO hotelResponseDTO = validateHotel(reservation.getHotelId());
        if (!Objects.equals(reservation.getUserId(), userId)){
            throw new EntityDoesntBelongException(String.format("Reservation with id %d doesn't belong to user with id %d", reservation.getId(), userId));
        }
        if (reservation.getCheckInDate().isBefore(LocalDate.now()) || reservation.getCheckInDate().equals(LocalDate.now())){
            throw new BusinessException("Your reservation time has already started, you can no longer cancel the reservation");
        }
        reservation.setCancellationDate(LocalDateTime.now());
        reservation.setStatus(Status.CANCELLED);
        repository.save(reservation);
        RoomResponseDTO roomResponseDTO = validateRoom(reservation.getRoomId());
        cancelReservationProducer.sendCancelReservationTopic(new ReservationKafka(
                userResponseDTO.firstName(),
                userResponseDTO.lastName(),
                userResponseDTO.email(),
                roomResponseDTO.roomNumber(),
                hotelResponseDTO.name(),
                hotelResponseDTO.country(),
                hotelResponseDTO.state(),
                hotelResponseDTO.city(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        ));
        updateRoom(roomResponseDTO, RoomStatus.AVAILABLE);
    }

    public ReservationGetResponseDTO findById(Long id) {
        return repository.findById(id).map(mapper::toReservationGetResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with id: %d, not found", id)));
    }

    public void updateReservation(ReservationPutRequestDTO reservationPutRequestDTO) {
        Reservation reservation = repository.findById(reservationPutRequestDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id: %d, not found", reservationPutRequestDTO.id())));
        if (reservationPutRequestDTO.hotelId() != null){
            reservation.setHotelId(reservationPutRequestDTO.hotelId());
        }
        if (reservationPutRequestDTO.userId() != null){
            reservation.setUserId(reservationPutRequestDTO.userId());
        }
        if (reservationPutRequestDTO.roomId() != null){
            reservation.setRoomId(reservationPutRequestDTO.roomId());
        }
        if (reservationPutRequestDTO.checkInDate() != null){
            reservation.setCheckInDate(reservationPutRequestDTO.checkInDate());
        }
        if (reservationPutRequestDTO.checkOutDate() != null){
            reservation.setCheckOutDate(reservationPutRequestDTO.checkOutDate());
        }
        if (reservationPutRequestDTO.status() != null){
            reservation.setStatus(reservationPutRequestDTO.status());
        }
        repository.save(reservation);
    }

    private UserResponseDTO validateUser(Long userId){
        return userClient.findUserById(userId, systemTokenService.generateToken())
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %d, not found", userId)));
    }

    private HotelResponseDTO validateHotel(Long hotelId){
        return hotelClient.findHotelById(hotelId, systemTokenService.generateToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id: %d, not found", hotelId)));
    }

    private RoomResponseDTO validateRoom(Long roomId){
        return roomClient.findRoomById(roomId, systemTokenService.generateToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with id: %d, not found", roomId)));
    }

    private void updateRoom(RoomResponseDTO roomResponseDTO, RoomStatus status){
        roomClient.updateRoom(new RoomRequestDTO(
                roomResponseDTO.id(),
                roomResponseDTO.roomNumber(),
                roomResponseDTO.hotelId(),
                roomResponseDTO.capacity(),
                roomResponseDTO.type(),
                status
        ), systemTokenService.generateToken());
    }

    private List<UserReservationResponseDTO> forEachReservation(List<Reservation> reservations){
        List<UserReservationResponseDTO> userReservationResponseDTOS = new ArrayList<>();
        for (Reservation reservation : reservations){
            HotelResponseDTO hotelResponseDTO = validateHotel(reservation.getHotelId());
            RoomResponseDTO roomResponseDTO = validateRoom(reservation.getRoomId());
            userReservationResponseDTOS.add(mapper.toUserReservationResponse(reservation, hotelResponseDTO, roomResponseDTO));
        }
        return userReservationResponseDTOS;
    }

    @Scheduled(fixedRate = 300000)
    public void updateRoomAfterCheckOut(){
        List<Reservation> reservations = repository.findAllByCheckOutDateBefore(LocalDate.now());
        for (Reservation reservation : reservations){
            RoomResponseDTO roomResponseDTO = validateRoom(reservation.getRoomId());
            updateRoom(roomResponseDTO, RoomStatus.AVAILABLE);
        }
    }
}
