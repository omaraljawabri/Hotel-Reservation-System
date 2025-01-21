package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.clients.ReservationClient;
import com.omar.hotel_reservation.clients.RoomClient;
import com.omar.hotel_reservation.dtos.request.PaymentRequestDTO;
import com.omar.hotel_reservation.dtos.request.RefundRequestDTO;
import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.PaymentResponseDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.entities.Payment;
import com.omar.hotel_reservation.entities.PaymentStatus;
import com.omar.hotel_reservation.exceptions.BusinessException;
import com.omar.hotel_reservation.mappers.PaymentMapper;
import com.omar.hotel_reservation.repositories.PaymentRepository;
import com.omar.hotel_reservation.utils.ReservationStatus;
import com.omar.hotel_reservation.utils.RoomStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationClient reservationClient;
    private final RoomClient roomClient;
    private final PaymentMapper mapper;

    @Transactional
    public PaymentResponseDTO reservationPayment(PaymentRequestDTO paymentRequestDTO) {
        ReservationResponseDTO reservationResponseDTO = validateReservation(paymentRequestDTO.reservationId());
        RoomResponseDTO roomResponseDTO = validateRoom(reservationResponseDTO.roomId());
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
        return mapper.toPaymentResponse(paymentSaved, reservationResponseDTO, roomResponseDTO);
        //to do -> send email to user notifying about payment confirmation
    }

    @Transactional
    public void refundPayment(RefundRequestDTO refundRequestDTO) {
        Payment payment = paymentRepository.findById(refundRequestDTO.paymentId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Payment with id: %d, not found", refundRequestDTO.paymentId())));
        ReservationResponseDTO reservationResponseDTO = validateReservation(refundRequestDTO.reservationId());
        RoomResponseDTO roomResponseDTO = validateRoom(reservationResponseDTO.roomId());
        if (!Objects.equals(payment.getReservationId(), refundRequestDTO.reservationId())){
            throw new BusinessException(String.format("Reservation with id %d doesn't belong to payment with id %d", refundRequestDTO.reservationId(), refundRequestDTO.paymentId()));
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundDate(LocalDateTime.now());
        paymentRepository.save(payment);
        updateReservation(reservationResponseDTO, payment.getUserId() ,ReservationStatus.CANCELLED);
        updateRoom(roomResponseDTO, RoomStatus.AVAILABLE);
        //to do -> send email to user notifying about refunding
    }

    private RoomResponseDTO validateRoom(Long roomId){
        return roomClient.findRoomById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with id: %d, not found", roomId)));
    }

    private ReservationResponseDTO validateReservation(Long reservationId){
        return reservationClient.findReservationById(reservationId)
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
        ));
    }

    private void updateRoom(RoomResponseDTO roomResponseDTO, RoomStatus status){
        roomClient.updateRoom(new RoomRequestDTO(
                roomResponseDTO.id(),
                roomResponseDTO.roomNumber(),
                roomResponseDTO.hotelId(),
                roomResponseDTO.capacity(),
                roomResponseDTO.type(),
                status
        ));
    }
}
