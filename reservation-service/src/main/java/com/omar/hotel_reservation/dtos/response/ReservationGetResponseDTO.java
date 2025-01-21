package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.entities.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationGetResponseDTO(
        Long id,
        Long hotelId,
        Long roomId,
        Long userId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        LocalDateTime bookingDate,
        LocalDateTime cancellationDate,
        Status status
) {
}
