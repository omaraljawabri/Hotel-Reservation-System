package com.omar.hotel_reservation.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequestDTO(
        @NotNull(message = "Hotel id is required")
        Long hotelId,
        @NotNull(message = "Room id is required")
        Long roomId,
        @NotNull(message = "User id is required")
        Long userId,
        @NotNull(message = "Date of check in is required")
        LocalDate checkInDate,
        @NotNull(message = "Date of check out is required")
        LocalDate checkOutDate
) {
}
