package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "reservation-client",
        url = "http://localhost:8030/api/v1/reservation" // change port to gateway port
)
public interface ReservationClient {

    @GetMapping("/{id}")
    Optional<ReservationResponseDTO> findReservationById(@PathVariable("id") Long id);

    @PutMapping
    void updateReservation(@RequestBody ReservationRequestDTO reservationRequestDTO);
}
