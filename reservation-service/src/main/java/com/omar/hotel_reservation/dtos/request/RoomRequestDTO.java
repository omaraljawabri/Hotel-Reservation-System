package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.utils.RoomStatus;
import com.omar.hotel_reservation.utils.RoomType;

public record RoomRequestDTO(
        Long id,
        Integer roomNumber,
        Long hotelId,
        Integer capacity,
        RoomType type,
        RoomStatus status
) {
}
