package com.omar.hotel_reservation.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull(message = "Email is required")
        @Email
        @Schema(description = "User email", example = "example@example.com", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @NotNull(message = "Password is required")
        @Schema(description = "User password", example = "password", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String password
) {
}
