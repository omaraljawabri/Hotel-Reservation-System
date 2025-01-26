package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotNull(message = "First name is required")
        @Schema(description = "User first name", example = "Adam", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String firstName,
        @NotNull(message = "Last name is required")
        @Schema(description = "User last name", example = "Richards", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String lastName,
        @NotNull(message = "Email is required")
        @Email
        @Schema(description = "User email", example = "example@example.com", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @NotNull(message = "Password is required")
        @Schema(description = "User password", example = "password", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String password,
        @NotNull(message = "Role is required")
        @Schema(description = "User role", example = "ADMIN", allowableValues = {"ADMIN", "USER"}, requiredMode = Schema.RequiredMode.REQUIRED)
        UserRole role
) {
}
