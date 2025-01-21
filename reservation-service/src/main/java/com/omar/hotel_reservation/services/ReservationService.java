package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.clients.HotelClient;
import com.omar.hotel_reservation.clients.RoomClient;
import com.omar.hotel_reservation.clients.UserClient;
import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.*;
import com.omar.hotel_reservation.entities.Reservation;
import com.omar.hotel_reservation.entities.Status;
import com.omar.hotel_reservation.exceptions.BusinessException;
import com.omar.hotel_reservation.exceptions.EntityDoesntBelongException;
import com.omar.hotel_reservation.exceptions.UserNotFoundException;
import com.omar.hotel_reservation.mappers.ReservationMapper;
import com.omar.hotel_reservation.repositories.ReservationRepository;
import com.omar.hotel_reservation.utils.HotelStatus;
import com.omar.hotel_reservation.utils.RoomStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // to do -> send email to user
        updateRoom(roomResponseDTO);
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

    private UserResponseDTO validateUser(Long userId){
        return userClient.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %d, not found", userId)));
    }

    private HotelResponseDTO validateHotel(Long hotelId){
        return hotelClient.findHotelById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id: %d, not found", hotelId)));
    }

    private RoomResponseDTO validateRoom(Long roomId){
        return roomClient.findRoomById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with id: %d, not found", roomId)));
    }

    private void updateRoom(RoomResponseDTO roomResponseDTO){
        roomClient.updateRoom(new RoomRequestDTO(
                roomResponseDTO.id(),
                roomResponseDTO.roomNumber(),
                roomResponseDTO.hotelId(),
                roomResponseDTO.capacity(),
                roomResponseDTO.type(),
                RoomStatus.RESERVED
        ));
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
}
