package com.omar.hotel_reservation.infra.handler;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage(
    LocalDateTime timestamp,
    String title,
    String message,
    HttpStatus httpStatus,
    int statusCode
) {
}
