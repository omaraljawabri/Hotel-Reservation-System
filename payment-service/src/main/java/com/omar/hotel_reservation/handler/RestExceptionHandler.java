package com.omar.hotel_reservation.handler;

import com.omar.hotel_reservation.exceptions.BusinessException;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorMessage> handler(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Entity not found")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build()
        );
    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<ErrorMessage> handler(BusinessException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Business exception")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(FeignException.class)
    private ResponseEntity<ErrorMessage> handler(FeignException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Error during trying to establish synchronous connection with other service")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }
}
