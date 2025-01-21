package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotNull(message = "Reservation id is required")
        Long reservationId,
        @NotNull(message = "Amount is required")
        BigDecimal amount,
        @NotNull(message = "Currency is required")
        String currency,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}
