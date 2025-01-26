package com.omar.hotel_reservation.dtos.request;

import com.omar.hotel_reservation.entities.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotNull(message = "Reservation id is required")
        @Schema(description = "Reservation id", example = "1", type = "long", requiredMode = Schema.RequiredMode.REQUIRED)
        Long reservationId,
        @NotNull(message = "Amount is required")
        @Schema(description = "Payment amount", example = "789.99", type = "BigDecimal", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal amount,
        @NotNull(message = "Currency is required")
        @Schema(description = "Payment currency", example = "$", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String currency,
        @NotNull(message = "Payment method is required")
        @Schema(description = "Payment method", example = "CREDIT_CARD", allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "PAYPAL"}, type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        PaymentMethod paymentMethod
) {
}
