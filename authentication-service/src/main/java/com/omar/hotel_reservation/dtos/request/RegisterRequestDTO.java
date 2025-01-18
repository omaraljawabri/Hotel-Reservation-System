package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email
        String email,
        @NotNull(message = "Password is required")
        String password,
        @NotNull(message = "Role is required")
        UserRole role
) {
}
