package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RoomRequestDTO(
        @NotNull(message = "Room number is required")
        @Schema(description = "Room number", example = "1000", type = "int", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer roomNumber,
        @NotNull(message = "Hotel id is required")
        @Schema(description = "Hotel id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long hotelId,
        @NotNull(message = "Capacity is required")
        @Schema(description = "Room capacity", example = "4", type = "int", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer capacity,
        @NotNull(message = "Room type is required")
        @Schema(description = "Room type", example = "FAMILY", type = "string", allowableValues = {"SINGLE", "TWIN", "DOUBLE", "SUITE", "DELUXE", "FAMILY"}, requiredMode = Schema.RequiredMode.REQUIRED)
        RoomType type,
        @Schema(description = "Status of the room", example = "AVAILABLE", type = "string", allowableValues = {"AVAILABLE", "OCCUPIED", "MAINTENANCE", "RESERVED"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Room status is required")
        RoomStatus status
) {
}
