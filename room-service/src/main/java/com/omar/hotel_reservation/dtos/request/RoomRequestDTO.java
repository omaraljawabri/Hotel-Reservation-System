package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;
import jakarta.validation.constraints.NotNull;

public record RoomRequestDTO(
        @NotNull(message = "Room number is required")
        Integer roomNumber,
        @NotNull(message = "Hotel id is required")
        Long hotelId,
        @NotNull(message = "Capacity is required")
        Integer capacity,
        @NotNull(message = "Room type is required")
        RoomType type,
        @NotNull(message = "Room status is required")
        RoomStatus status
) {
}
