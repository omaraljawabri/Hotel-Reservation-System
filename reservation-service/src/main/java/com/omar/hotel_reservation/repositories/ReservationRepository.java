package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Reservation;
import com.omar.hotel_reservation.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(Long userId);
    List<Reservation> findAllByUserIdAndStatus(Long userId, Status status);
    @Query("SELECT r FROM Reservation r WHERE r.checkOutDate < :dateTime")
    List<Reservation> findAllByCheckOutDateBefore(@Param("dateTime") LocalDate dateTime);
}
