package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.utils.UserRole;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserRole role,
        boolean isVerified
) {
}
