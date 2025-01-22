package com.omar.hotel_reservation.producers.reservation;

import java.time.LocalDate;

public record ReservationKafka(
        String firstName,
        String lastName,
        String email,
        Integer roomNumber,
        String hotelName,
        String country,
        String state,
        String city,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {
}
