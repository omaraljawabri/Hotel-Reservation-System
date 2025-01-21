package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.utils.ReservationStatus;

import java.time.LocalDate;

public record ReservationRequestDTO(
        Long id,
        Long hotelId,
        Long roomId,
        Long userId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        ReservationStatus status
) {
}
