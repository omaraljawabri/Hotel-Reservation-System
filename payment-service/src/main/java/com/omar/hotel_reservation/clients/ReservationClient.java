package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "reservation-client",
        url = "http://gateway-service:8080/api/v1/reservation"
)
@Headers("Authorization: {token}")
public interface ReservationClient {

    @GetMapping("/{id}")
    Optional<ReservationResponseDTO> findReservationById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @PutMapping
    void updateReservation(@RequestBody ReservationRequestDTO reservationRequestDTO, @RequestHeader("Authorization") String token);
}
