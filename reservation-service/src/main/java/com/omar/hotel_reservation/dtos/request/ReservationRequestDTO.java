package com.omar.hotel_reservation.dtos.request;

import java.time.LocalDate;

public record ReservationRequestDTO(
       Long hotelId,
       Long roomId,
       Long userId,
       LocalDate checkInDate,
       LocalDate checkOutDate
) {
}
