package com.omar.hotel_reservation.kafka;

public record AuthConfirmation(
        String verificationLink,
        String firstName,
        String lastName,
        String email
) {
}
