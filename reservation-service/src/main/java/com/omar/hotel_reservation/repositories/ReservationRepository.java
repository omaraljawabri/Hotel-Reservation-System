package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
