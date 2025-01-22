package com.omar.hotel_reservation.producers.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentKafka(
        String firstName,
        String lastName,
        String email,
        PaymentMethod method,
        BigDecimal amount,
        String currency,
        LocalDateTime paymentDate,
        String hotelName,
        Integer roomNumber
) {
}
