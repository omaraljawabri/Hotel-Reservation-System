package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.ReservationPutRequestDTO;
import com.omar.hotel_reservation.dtos.request.ReservationRequestDTO;
import com.omar.hotel_reservation.dtos.response.ReservationGetResponseDTO;
import com.omar.hotel_reservation.dtos.response.ReservationResponseDTO;
import com.omar.hotel_reservation.dtos.response.UserReservationResponseDTO;
import com.omar.hotel_reservation.handler.ErrorMessage;
import com.omar.hotel_reservation.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@SecurityRequirement(name = "securityConfig")
@Tag(name = "ReservationController", description = "These endpoints are responsible for create, update and fetching data from Reservation entity")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "This endpoint is responsible for create a reservation",
            method = "POST",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody @Valid ReservationRequestDTO reservationRequestDTO){
        return new ResponseEntity<>(reservationService.createReservation(reservationRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is responsible for cancel a reservation by their id and user id",
            method = "POST",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping("/cancel/{id}/{user-id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("id") Long id, @PathVariable("user-id") Long userId){
        reservationService.cancelReservation(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "This endpoint is responsible for update a reservation",
            method = "PUT",
            description = "Needs ROLE_ADMIN or ROLE_USER to be accessed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateReservation(@RequestBody @Valid ReservationPutRequestDTO reservationPutRequestDTO){
        reservationService.updateReservation(reservationPutRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "This endpoint is responsible for get all reservations by user id",
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
    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<UserReservationResponseDTO>> findReservationByUserId(@PathVariable("user-id") Long userId){
        return ResponseEntity.ok(reservationService.findByUserId(userId));
    }

    @Operation(summary = "This endpoint is responsible for get all confirmed reservations by user id",
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
    @GetMapping("/confirmed/user/{user-id}")
    public ResponseEntity<List<UserReservationResponseDTO>> findConfirmedReservationByUserId(@PathVariable("user-id") Long userId){
        return ResponseEntity.ok(reservationService.findConfirmedByUserId(userId));
    }

    @Operation(summary = "This endpoint is responsible for get a reservation by their id",
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
    public ResponseEntity<ReservationGetResponseDTO> findReservationById(@PathVariable("id") Long id){
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @Operation(summary = "This endpoint is responsible for verify the service health",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/health")
    public ResponseEntity<Void> verifyServiceHealth(){
        return ResponseEntity.ok().build();
    }
}

