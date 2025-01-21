package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationPutRequestDTO(
        @NotNull(message = "Id is required")
        Long id,
        Long hotelId,
        Long roomId,
        Long userId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Status status
) {
}
