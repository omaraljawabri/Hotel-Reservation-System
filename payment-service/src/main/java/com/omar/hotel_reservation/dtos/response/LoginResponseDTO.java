package com.omar.hotel_reservation.dtos.response;

public record LoginResponseDTO(
        String token,
        String email
) {
}
