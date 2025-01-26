package com.omar.hotel_reservation.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RefundRequestDTO(
        @NotNull(message = "Payment id is required")
        @Schema(description = "Payment id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long paymentId,
        @NotNull(message = "Reservation id is required")
        @Schema(description = "Reservation id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long reservationId
) {
}
