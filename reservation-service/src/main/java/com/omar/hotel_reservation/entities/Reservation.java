package com.omar.hotel_reservation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hotelId;

    private Long roomId;

    private Long userId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private LocalDateTime bookingDate;

    private LocalDateTime cancellationDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
