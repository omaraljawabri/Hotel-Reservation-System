package com.omar.hotel_reservation.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequestDTO(
        @Email
        @NotNull(message = "Email is required")
        @Schema(description = "Email of user who wants to change their password", example = "example@example.com", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String email
) {
}
