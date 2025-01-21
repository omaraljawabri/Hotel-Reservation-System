package com.omar.hotel_reservation.exceptions;

public class EntityDoesntBelongException extends RuntimeException {
    public EntityDoesntBelongException(String message) {
        super(message);
    }

    public EntityDoesntBelongException(){
        super("Entity doesnt belong");
    }
}
