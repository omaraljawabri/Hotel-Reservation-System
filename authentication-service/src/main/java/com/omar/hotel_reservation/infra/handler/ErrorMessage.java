package com.omar.hotel_reservation.infra.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage(
        @Schema(description = "Error timestamp", example = "2025-01-26T18:08:30", type = "timestamp")
    LocalDateTime timestamp,
    @Schema(description = "Error title", example = "User not found", type = "string")
    String title,
    @Schema(description = "Exception message", example = "E-mail not found", type = "string")
    String message,
    @Schema(description = "Http Status", example = "NOT_FOUND", type = "http status")
    HttpStatus httpStatus,
    @Schema(description = "Number of http status", example = "404", type = "int")
    int statusCode
) {
}
