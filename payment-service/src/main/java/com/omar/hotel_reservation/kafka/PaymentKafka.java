package com.omar.hotel_reservation.kafka;

import com.omar.hotel_reservation.entities.PaymentMethod;

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
