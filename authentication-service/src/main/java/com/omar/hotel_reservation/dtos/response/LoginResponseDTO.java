package com.omar.hotel_reservation.dtos.response;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
        String token,
        String email
) {
}
