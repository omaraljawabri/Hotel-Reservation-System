package com.omar.hotel_reservation.dtos.request;

import jakarta.validation.constraints.NotNull;

public record RefundRequestDTO(
        @NotNull(message = "Payment id is required")
        Long paymentId,
        @NotNull(message = "Reservation id is required")
        Long reservationId
) {
}
