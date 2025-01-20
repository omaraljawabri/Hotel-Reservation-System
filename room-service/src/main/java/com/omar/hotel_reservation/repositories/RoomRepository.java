package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.Room;
import com.omar.hotel_reservation.entities.RoomStatus;
import com.omar.hotel_reservation.entities.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByHotelId(Long hotelId, Pageable pageable);
    List<Room> findAllByHotelIdAndStatus(Long hotelId, RoomStatus status);
    List<Room> findAllByType(RoomType type);
}
