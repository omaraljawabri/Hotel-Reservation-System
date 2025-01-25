package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "room-service",
        url = "http://localhost:8080/api/v1/room"
)
@Headers("Authorization: {token}")
public interface RoomClient {

    @GetMapping("/{id}")
    Optional<RoomResponseDTO> findRoomById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PutMapping
    RoomResponseDTO updateRoom(@RequestBody RoomRequestDTO roomRequestDTO, @RequestHeader("Authorization") String token);

}
