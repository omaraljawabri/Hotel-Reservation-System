package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationPutRequestDTO(
        @NotNull(message = "Id is required")
        @Schema(description = "Reservation id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
        @Schema(description = "Hotel id", example = "1", type = "long", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long hotelId,
        @Schema(description = "Room id", example = "1", type = "long", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long roomId,
        @Schema(description = "User id", example = "1", type = "long", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long userId,
        @Schema(description = "Reservation check in date", example = "2025-01-26T21:41:07", type = "date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate checkInDate,
        @Schema(description = "Reservation check out date", example = "2025-02-12T12:38:00", type = "date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDate checkOutDate,
        @Schema(description = "Reservation status", example = "PENDING", allowableValues = {"PENDING", "CANCELLED", "CONFIRMED"}, type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Status status
) {
}
