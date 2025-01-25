package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.clients.HotelClient;
import com.omar.hotel_reservation.clients.ReservationClient;
import com.omar.hotel_reservation.clients.RoomClient;
import com.omar.hotel_reservation.clients.UserClient;
import com.omar.hotel_reservation.dtos.request.PaymentRequestDTO;
import com.omar.hotel_reservation.dtos.request.RefundRequestDTO;
import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.*;
import com.omar.hotel_reservation.entities.Payment;
import com.omar.hotel_reservation.entities.PaymentStatus;
import com.omar.hotel_reservation.exceptions.BusinessException;
import com.omar.hotel_reservation.kafka.PaymentConfirmationProducer;
import com.omar.hotel_reservation.kafka.PaymentKafka;
import com.omar.hotel_reservation.kafka.PaymentRefundProducer;
import com.omar.hotel_reservation.mappers.PaymentMapper;
import com.omar.hotel_reservation.repositories.PaymentRepository;
import com.omar.hotel_reservation.utils.ReservationStatus;
import com.omar.hotel_reservation.utils.RoomStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationClient reservationClient;
    private final RoomClient roomClient;
    private final UserClient userClient;
    private final HotelClient hotelClient;
    private final SystemTokenService systemTokenService;
    private final PaymentMapper mapper;
    private final PaymentConfirmationProducer paymentConfirmationProducer;
    private final PaymentRefundProducer paymentRefundProducer;

    @Transactional
    public PaymentResponseDTO reservationPayment(PaymentRequestDTO paymentRequestDTO) {
        ReservationResponseDTO reservationResponseDTO = validateReservation(paymentRequestDTO.reservationId());
        RoomResponseDTO roomResponseDTO = validateRoom(reservationResponseDTO.roomId());
        UserResponseDTO userResponseDTO = validateUser(reservationResponseDTO.userId());
        HotelResponseDTO hotelResponseDTO = validateHotel(roomResponseDTO.hotelId());
        if (reservationResponseDTO.status() != ReservationStatus.PENDING){
            throw new BusinessException(String.format("Reservation with id %d is not able to be payed", reservationResponseDTO.id()));
        }
        if (roomResponseDTO.status() != RoomStatus.RESERVED){
            throw new BusinessException(String.format("Room with id %d is not able to be payed", roomResponseDTO.id()));
        }
        Payment payment = mapper.toPayment(paymentRequestDTO, reservationResponseDTO.userId());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment paymentSaved = paymentRepository.save(payment);
        updateReservation(reservationResponseDTO, reservationResponseDTO.userId(), ReservationStatus.CONFIRMED);
        updateRoom(roomResponseDTO, RoomStatus.OCCUPIED);
        paymentConfirmationProducer.sendPaymentConfirmation(new PaymentKafka(
                userResponseDTO.firstName(),
                userResponseDTO.lastName(),
                userResponseDTO.email(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentDate(),
                hotelResponseDTO.name(),
                roomResponseDTO.roomNumber()
        ));
        return mapper.toPaymentResponse(paymentSaved, reservationResponseDTO, roomResponseDTO);
    }

    @Transactional
    public void refundPayment(RefundRequestDTO refundRequestDTO) {
        Payment payment = paymentRepository.findById(refundRequestDTO.paymentId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Payment with id: %d, not found", refundRequestDTO.paymentId())));
        ReservationResponseDTO reservationResponseDTO = validateReservation(refundRequestDTO.reservationId());
        RoomResponseDTO roomResponseDTO = validateRoom(reservationResponseDTO.roomId());
        UserResponseDTO userResponseDTO = validateUser(reservationResponseDTO.userId());
        HotelResponseDTO hotelResponseDTO = validateHotel(roomResponseDTO.hotelId());
        if (!Objects.equals(payment.getReservationId(), refundRequestDTO.reservationId())){
            throw new BusinessException(String.format("Reservation with id %d doesn't belong to payment with id %d", refundRequestDTO.reservationId(), refundRequestDTO.paymentId()));
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundDate(LocalDateTime.now());
        paymentRepository.save(payment);
        updateReservation(reservationResponseDTO, payment.getUserId() ,ReservationStatus.CANCELLED);
        updateRoom(roomResponseDTO, RoomStatus.AVAILABLE);
        paymentRefundProducer.sendPaymentRefund(new PaymentKafka(
                userResponseDTO.firstName(),
                userResponseDTO.lastName(),
                userResponseDTO.email(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentDate(),
                hotelResponseDTO.name(),
                roomResponseDTO.roomNumber()
        ));
    }

    private RoomResponseDTO validateRoom(Long roomId){
        return roomClient.findRoomById(roomId, systemTokenService.generateToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with id: %d, not found", roomId)));
    }

    private HotelResponseDTO validateHotel(Long hotelId){
        return hotelClient.findHotelById(hotelId, systemTokenService.generateToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id: %d, not found", hotelId)));
    }

    private UserResponseDTO validateUser(Long userId){
        return userClient.findUserById(userId, systemTokenService.generateToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d, not found", userId)));
    }

    private ReservationResponseDTO validateReservation(Long reservationId){
        return reservationClient.findReservationById(reservationId, systemTokenService.generateToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with id: %d, not found", reservationId)));
    }

    private void updateReservation(ReservationResponseDTO reservationResponseDTO,
                                   Long userId, ReservationStatus status){
        reservationClient.updateReservation(new ReservationRequestDTO(
                reservationResponseDTO.id(),
                reservationResponseDTO.hotelId(),
                reservationResponseDTO.roomId(),
                userId,
                reservationResponseDTO.checkInDate(),
                reservationResponseDTO.checkOutDate(),
                status
        ), systemTokenService.generateToken());
    }

    private void updateRoom(RoomResponseDTO roomResponseDTO, RoomStatus status) {
        roomClient.updateRoom(new RoomRequestDTO(
                roomResponseDTO.id(),
                roomResponseDTO.roomNumber(),
                roomResponseDTO.hotelId(),
                roomResponseDTO.capacity(),
                roomResponseDTO.type(),
                status
        ), systemTokenService.generateToken());
    }

}
