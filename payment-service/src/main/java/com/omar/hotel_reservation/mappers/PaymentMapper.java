package com.omar.hotel_reservation.mappers;

import com.omar.hotel_reservation.dtos.request.PaymentRequestDTO;
import com.omar.hotel_reservation.dtos.response.PaymentResponseDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequestDTO paymentRequestDTO, Long userId){
        return Payment.builder()
                .reservationId(paymentRequestDTO.reservationId())
                .userId(userId)
                .amount(paymentRequestDTO.amount())
                .currency(paymentRequestDTO.currency())
                .paymentMethod(paymentRequestDTO.paymentMethod())
                .build();
    }

    public PaymentResponseDTO toPaymentResponse(Payment payment, ReservationResponseDTO reservationResponseDTO, RoomResponseDTO roomResponseDTO){
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getReservationId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                reservationResponseDTO,
                roomResponseDTO
        );
    }
}
