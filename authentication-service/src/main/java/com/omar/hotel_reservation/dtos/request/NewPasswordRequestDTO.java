package com.omar.hotel_reservation.dtos.request;

import jakarta.validation.constraints.NotNull;

public record NewPasswordRequestDTO(
        @NotNull(message = "New password should not be null")
        String newPassword
) {
}
