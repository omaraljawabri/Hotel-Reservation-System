package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RoomPutRequestDTO(
        @NotNull(message = "Room id is required")
        @Schema(description = "Room id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
        @Schema(description = "Room number", example = "1000", type = "int", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer roomNumber,
        @Schema(description = "Hotel id", example = "1", type = "long", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long hotelId,
        @Schema(description = "Room capacity", example = "4", type = "int", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer capacity,
        @Schema(description = "Room type", example = "FAMILY", type = "string", allowableValues = {"SINGLE", "TWIN", "DOUBLE", "SUITE", "DELUXE", "FAMILY"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        RoomType type,
        @Schema(description = "Status of the room", example = "AVAILABLE", type = "string", allowableValues = {"AVAILABLE", "OCCUPIED", "MAINTENANCE", "RESERVED"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        RoomStatus status
) {
}
