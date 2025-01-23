package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "room-service",
        url = "http://localhost:8080/api/v1/room"
)
public interface RoomClient {

    @GetMapping("/{id}")
    Optional<RoomResponseDTO> findRoomById(@PathVariable("id") Long id);

    @PutMapping
    void updateRoom(@RequestBody RoomRequestDTO roomRequestDTO);
}
