package com.omar.hotel_reservation.producers.auth;

public record AuthConfirmation(
        String verificationLink,
        String firstName,
        String lastName,
        String email
) {
}
