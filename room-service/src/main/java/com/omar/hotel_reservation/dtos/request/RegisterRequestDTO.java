package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.utils.UserRole;

public record RegisterRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        UserRole role
) {
}
