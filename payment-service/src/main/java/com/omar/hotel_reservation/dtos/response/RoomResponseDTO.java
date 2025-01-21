package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.utils.RoomStatus;
import com.omar.hotel_reservation.utils.RoomType;

public record RoomResponseDTO(
        Long id,
        Integer roomNumber,
        Long hotelId,
        Integer capacity,
        RoomType type,
        RoomStatus status
) {
}
