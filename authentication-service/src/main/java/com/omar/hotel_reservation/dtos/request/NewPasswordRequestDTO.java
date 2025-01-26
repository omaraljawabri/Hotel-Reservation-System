package com.omar.hotel_reservation.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record NewPasswordRequestDTO(
        @NotNull(message = "New password should not be null")
        @Schema(description = "User new password", example = "newpassword", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String newPassword
) {
}
