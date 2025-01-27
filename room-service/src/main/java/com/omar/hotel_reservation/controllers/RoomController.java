package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.RoomPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.RoomRequestDTO;
import com.omar.hotel_reservation.dtos.response.RoomResponseDTO;
import com.omar.hotel_reservation.entities.Room;
import com.omar.hotel_reservation.entities.RoomType;
import com.omar.hotel_reservation.handler.ErrorMessage;
import com.omar.hotel_reservation.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
@SecurityRequirement(name = "securityConfig")
@Tag(name = "RoomController", description = "These endpoints are responsible for create, update and fetch data from Room Entity")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "This endpoint is responsible for create a room",
            method = "POST",
            description = "Needs ROLE_ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody @Valid RoomRequestDTO roomRequestDTO){
        return new ResponseEntity<>(roomService.createRoom(roomRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is responsible for update a room",
            method = "PUT",
            description = "Needs ROLE_ADMIN to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomResponseDTO> updateRoom(@RequestBody @Valid RoomPutRequestDTO roomPutRequestDTO){
        return ResponseEntity.ok(roomService.updateRoom(roomPutRequestDTO));
    }

    @Operation(summary = "This endpoint is responsible for get all rooms paginated by hotel id",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/hotel/{hotel-id}")
    public ResponseEntity<Page<Room>> findRoomsByHotelId(
            @PathVariable("hotel-id") Long hotelId,
            @RequestParam int page,
            @RequestParam int quantity
    ){
        return ResponseEntity.ok(roomService.findRoomsByHotelId(hotelId, page, quantity));
    }

    @Operation(summary = "This endpoint is responsible for get all rooms available by hotel id",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/available/{hotel-id}")
    public ResponseEntity<List<Room>> findRoomsAvailableByHotelId(@PathVariable("hotel-id") Long hotelId){
        return ResponseEntity.ok(roomService.findRoomsAvailableByHotelId(hotelId));
    }

    @Operation(summary = "This endpoint is responsible for get a room by their id",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Room> findRoomById(@PathVariable("id") Long id){
        return ResponseEntity.ok(roomService.findById(id));
    }

    @Operation(summary = "This endpoint is responsible for get all rooms paginated",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping
    public ResponseEntity<Page<Room>> findAllPaginated(@RequestParam int page, @RequestParam int quantity){
        return ResponseEntity.ok(roomService.findAllPaginated(page, quantity));
    }

    @Operation(summary = "This endpoint is responsible for get all rooms by their type",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/type")
    public ResponseEntity<List<Room>> findRoomsByType(@RequestParam RoomType type){
        return ResponseEntity.ok(roomService.findRoomsByType(type));
    }
}