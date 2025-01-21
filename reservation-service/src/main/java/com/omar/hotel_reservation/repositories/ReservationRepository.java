package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserId(Long userId);
}
