package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.HotelPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.HotelRequestDTO;
import com.omar.hotel_reservation.entities.Hotel;
import com.omar.hotel_reservation.handler.ErrorMessage;
import com.omar.hotel_reservation.service.HotelService;
import com.omar.hotel_reservation.specifications.HotelQueryFilter;
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
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
@SecurityRequirement(name = "securityConfig")
@Tag(name = "HotelController", description = "These endpoints are responsible for handling data from hotel entity")
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "This endpoint is responsible for create a new hotel",
            method = "POST",
            description = "Needs ROLE_ADMIN to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hotel> createHotel(@RequestBody @Valid HotelRequestDTO hotelRequestDTO){
        return new ResponseEntity<>(hotelService.createHotel(hotelRequestDTO) ,HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is responsible for update an existing hotel",
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
    public ResponseEntity<Hotel> updateHotel(@RequestBody @Valid HotelPutRequestDTO hotelPutRequestDTO){
        return ResponseEntity.ok(hotelService.updateHotel(hotelPutRequestDTO));
    }

    @Operation(summary = "This endpoint is responsible for get all hotels registered paginated",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping
    public ResponseEntity<Page<Hotel>> findAllHotels(@RequestParam int page, @RequestParam int quantity){
        return ResponseEntity.ok(hotelService.findAll(page, quantity));
    }

    @Operation(summary = "This endpoint is responsible for get an hotel by their id",
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
    public ResponseEntity<Hotel> findHotelById(@PathVariable("id") Long id){
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @Operation(summary = "This endpoint is responsible for get an hotel filtered by their attributes",
            method = "GET",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<Hotel>> findHotelByFilters(HotelQueryFilter hotelQueryFilter){
        return ResponseEntity.ok(hotelService.findByFilters(hotelQueryFilter));
    }
}
