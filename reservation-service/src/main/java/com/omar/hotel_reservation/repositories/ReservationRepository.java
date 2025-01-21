package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Reservation;
import com.omar.hotel_reservation.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(Long userId);
    List<Reservation> findAllByUserIdAndStatus(Long userId, Status status);
}
