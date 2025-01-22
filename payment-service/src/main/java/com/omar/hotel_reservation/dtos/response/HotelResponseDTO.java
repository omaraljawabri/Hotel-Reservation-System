package com.omar.hotel_reservation.dtos.response;

import com.omar.hotel_reservation.utils.HotelCategory;
import com.omar.hotel_reservation.utils.HotelStatus;

public record HotelResponseDTO(
        Long id,
        String name,
        String description,
        String address,
        String country,
        String state,
        String city,
        String postalCode,
        HotelStatus status,
        HotelCategory category
) {
}
