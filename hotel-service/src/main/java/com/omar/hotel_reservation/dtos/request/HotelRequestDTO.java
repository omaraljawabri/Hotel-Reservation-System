package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.Category;
import com.omar.hotel_reservation.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record HotelRequestDTO(
        @NotNull(message = "Name is required")
        @Schema(description = "Hotel name", example = "Sunset Inn 2", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
        @Schema(description = "Hotel description", example = "A very comfortable four stars hotel", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String description,
        @Schema(description = "Hotel address", example = "123 Ocean Drive", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String address,
        @NotNull(message = "Country is required")
        @Schema(description = "Hotel country", example = "USA", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String country,
        @NotNull(message = "State is required")
        @Schema(description = "Hotel state", example = "California", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String state,
        @NotNull(message = "City is required")
        @Schema(description = "Hotel city", example = "Santa Monica", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String city,
        @Schema(description = "Hotel postal code", example = "90401", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String postalCode,
        @NotNull(message = "Status is required")
        @Schema(description = "Hotel status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "MAINTENANCE"}, type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        Status status,
        @Schema(description = "Hotel category", example = "FOUR_STAR", allowableValues = {"ONE_STAR", "TWO_STAR", "THREE_STAR", "FOUR_STAR", "FIVE_STAR"}, type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Category category
) {
}
