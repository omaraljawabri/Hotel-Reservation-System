package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;

public record RoomPutRequestDTO(
        Long id,
        Integer roomNumber,
        Long hotelId,
        Integer capacity,
        RoomType type,
        RoomStatus status
) {
}
