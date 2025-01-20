package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.utils.Category;
import com.omar.hotel_reservation.utils.Status;

public record HotelResponseDTO(
        Long id,
        String name,
        String description,
        String address,
        String country,
        String state,
        String city,
        String postalCode,
        Status status,
        Category category
) {
}
