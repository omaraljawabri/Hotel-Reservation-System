package com.omar.hotel_reservation.infra.handler;

import com.omar.hotel_reservation.exceptions.InvalidCodeException;
import com.omar.hotel_reservation.exceptions.UserAlreadyExistsException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidCodeException.class)
    private ResponseEntity<ErrorMessage> handler(InvalidCodeException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Invalid code")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<ErrorMessage> handler(UsernameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Username not found")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<ErrorMessage> handler(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("User already exists")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ErrorMessage> handler(ValidationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Object is not valid")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorMessage> handler(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad credentials")
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }
}
