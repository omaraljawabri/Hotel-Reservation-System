package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.entities.PaymentMethod;
import com.omar.hotel_reservation.entities.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(
        Long id,
        Long reservationId,
        Long userId,
        BigDecimal amount,
        String currency,
        LocalDateTime paymentDate,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        ReservationResponseDTO reservation,
        RoomResponseDTO room
) {
}
