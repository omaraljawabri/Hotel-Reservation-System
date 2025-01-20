package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;
import lombok.Builder;

@Builder
public record RoomResponseDTO(
        Long id,
        Integer roomNumber,
        Integer capacity,
        RoomType type,
        RoomStatus status,
        HotelResponseDTO hotel
) {
}
