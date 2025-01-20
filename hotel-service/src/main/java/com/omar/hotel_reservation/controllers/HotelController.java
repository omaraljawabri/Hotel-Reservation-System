package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.HotelPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.HotelRequestDTO;
import com.omar.hotel_reservation.entities.Hotel;
import com.omar.hotel_reservation.service.HotelService;
import com.omar.hotel_reservation.specifications.HotelQueryFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody @Valid HotelRequestDTO hotelRequestDTO){
        return new ResponseEntity<>(hotelService.createHotel(hotelRequestDTO) ,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Hotel> updateHotel(@RequestBody @Valid HotelPutRequestDTO hotelPutRequestDTO){
        return ResponseEntity.ok(hotelService.updateHotel(hotelPutRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Hotel>> findAllHotels(@RequestParam int page, @RequestParam int quantity){
        return ResponseEntity.ok(hotelService.findAll(page, quantity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable("id") Long id){
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Hotel>> findHotelByFilters(HotelQueryFilter hotelQueryFilter){
        return ResponseEntity.ok(hotelService.findByFilters(hotelQueryFilter));
    }
}
