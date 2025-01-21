package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.ReservationPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.response.ReservationGetResponseDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import com.omar.hotel_reservation.dtos.response.UserReservationResponseDTO;
import com.omar.hotel_reservation.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO){
        return new ResponseEntity<>(reservationService.createReservation(reservationRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/cancel/{id}/{user-id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") Long id, @PathVariable("user-id") Long userId){
        reservationService.cancelReservation(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateReservation(@RequestBody ReservationPutRequestDTO reservationPutRequestDTO){
        reservationService.updateReservation(reservationPutRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<UserReservationResponseDTO>> findReservationByUserId(@PathVariable("user-id") Long userId){
        return ResponseEntity.ok(reservationService.findByUserId(userId));
    }

    @GetMapping("/confirmed/user/{user-id}")
    public ResponseEntity<List<UserReservationResponseDTO>> findConfirmedReservationByUserId(@PathVariable("user-id") Long userId){
        return ResponseEntity.ok(reservationService.findConfirmedByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationGetResponseDTO> findReservationById(@PathVariable("id") Long id){
        return ResponseEntity.ok(reservationService.findById(id));
    }
}

