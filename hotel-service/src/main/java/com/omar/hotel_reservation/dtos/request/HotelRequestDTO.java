package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.Category;
import com.omar.hotel_reservation.entities.Status;
import jakarta.validation.constraints.NotNull;

public record HotelRequestDTO(
        @NotNull(message = "Name is required")
        String name,
        String description,
        String address,
        @NotNull(message = "Country is required")
        String country,
        @NotNull(message = "State is required")
        String state,
        @NotNull(message = "City is required")
        String city,
        String postalCode,
        @NotNull(message = "Status is required")
        Status status,
        Category category
) {
}
