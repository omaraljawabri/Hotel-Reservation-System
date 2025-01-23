package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.response.HotelResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "hotel-service",
        url = "http://localhost:8080/api/v1/hotel"
)
public interface HotelClient {

    @GetMapping("/{id}")
    Optional<HotelResponseDTO> findHotelById(@PathVariable("id") Long id);
}
