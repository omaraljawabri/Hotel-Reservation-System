package com.omar.hotel_reservation.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull(message = "Email is required")
        @Email
        String email,
        @NotNull(message = "Password is required")
        String password
) {
}
