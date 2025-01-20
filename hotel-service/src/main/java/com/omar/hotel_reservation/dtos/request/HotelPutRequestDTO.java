package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.Category;
import com.omar.hotel_reservation.entities.Status;
import jakarta.validation.constraints.NotNull;

public record HotelPutRequestDTO(
        @NotNull(message = "hotelId is required")
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
