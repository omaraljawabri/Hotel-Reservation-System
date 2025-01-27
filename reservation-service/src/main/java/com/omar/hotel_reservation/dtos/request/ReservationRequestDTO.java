package com.omar.hotel_reservation.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequestDTO(
        @NotNull(message = "Hotel id is required")
        @Schema(description = "Hotel id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long hotelId,
        @NotNull(message = "Room id is required")
        @Schema(description = "Room id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long roomId,
        @NotNull(message = "User id is required")
        @Schema(description = "User id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long userId,
        @NotNull(message = "Date of check in is required")
        @Schema(description = "Reservation check in date", example = "2025-01-26T21:41:07", type = "date", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate checkInDate,
        @NotNull(message = "Date of check out is required")
        @Schema(description = "Reservation check out date", example = "2025-02-12T12:38:00", type = "date", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate checkOutDate
) {
}
