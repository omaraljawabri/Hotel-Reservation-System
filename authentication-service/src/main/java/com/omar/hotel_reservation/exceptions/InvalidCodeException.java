package com.omar.hotel_reservation.exceptions;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(String message) {
        super(message);
    }

    public InvalidCodeException(){
        super("Code not found");
    }
}
