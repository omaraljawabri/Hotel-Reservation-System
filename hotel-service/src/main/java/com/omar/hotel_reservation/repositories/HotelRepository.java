package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAll(Specification<Hotel> hotelSpecification);
}
