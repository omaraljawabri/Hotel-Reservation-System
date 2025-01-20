package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.RoomPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.entities.Room;
import com.omar.hotel_reservation.entities.RoomType;
import com.omar.hotel_reservation.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody @Valid RoomRequestDTO roomRequestDTO){
        return new ResponseEntity<>(roomService.createRoom(roomRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RoomResponseDTO> updateRoom(@RequestBody @Valid RoomPutRequestDTO roomPutRequestDTO){
        return ResponseEntity.ok(roomService.updateRoom(roomPutRequestDTO));
    }

    @GetMapping("/hotel/{hotel-id}")
    public ResponseEntity<Page<Room>> findRoomsByHotelId(
            @PathVariable("hotel-id") Long hotelId,
            @RequestParam int page,
            @RequestParam int quantity
    ){
        return ResponseEntity.ok(roomService.findRoomsByHotelId(hotelId, page, quantity));
    }

    @GetMapping("/available/{hotel-id}")
    public ResponseEntity<List<Room>> findRoomsAvailableByHotelId(@PathVariable("hotel-id") Long hotelId){
        return ResponseEntity.ok(roomService.findRoomsAvailableByHotelId(hotelId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findRoomById(@PathVariable("id") Long id){
        return ResponseEntity.ok(roomService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Room>> findAllPaginated(@RequestParam int page, @RequestParam int quantity){
        return ResponseEntity.ok(roomService.findAllPaginated(page, quantity));
    }

    @GetMapping("/type")
    public ResponseEntity<List<Room>> findRoomsByType(@RequestParam RoomType type){
        return ResponseEntity.ok(roomService.findRoomsByType(type));
    }
}