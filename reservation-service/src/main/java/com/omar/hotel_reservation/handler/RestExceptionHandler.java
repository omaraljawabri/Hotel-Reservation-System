package com.omar.hotel_reservation.handler;

import com.omar.hotel_reservation.exceptions.BusinessException;
import com.omar.hotel_reservation.exceptions.EntityDoesntBelongException;
import com.omar.hotel_reservation.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

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

    @ExceptionHandler(EntityDoesntBelongException.class)
    private ResponseEntity<ErrorMessage> handler(EntityDoesntBelongException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Entity doesnt belong")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorMessage> handler(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("User not found")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build()
        );
    }

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
}
