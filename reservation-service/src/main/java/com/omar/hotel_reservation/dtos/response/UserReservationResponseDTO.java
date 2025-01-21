package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.entities.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserReservationResponseDTO(
        Long id,
        Long hotelId,
        Long roomId,
        Long userId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        LocalDateTime bookingDate,
        Status status,
        HotelResponseDTO hotel,
        RoomResponseDTO room
) {
}
