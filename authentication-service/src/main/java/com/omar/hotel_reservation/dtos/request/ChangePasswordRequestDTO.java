package com.omar.hotel_reservation.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequestDTO(
        @Email
        @NotNull(message = "Email is required")
        String email
) {
}
