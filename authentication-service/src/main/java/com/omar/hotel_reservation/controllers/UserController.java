package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.response.UserResponseDTO;
import com.omar.hotel_reservation.infra.handler.ErrorMessage;
import com.omar.hotel_reservation.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "These endpoints are responsible for fetching data from users")
public class UserController {

    private final UserService service;

    @Operation(summary = "This endpoint is responsible for get an user by their id",
            method = "GET",
            description = "Needs ROLE_ADMIN to be accessed")
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
    @SecurityRequirement(name = "securityConfig")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "This endpoint is responsible for validate if an user exists by their email",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsById(@PathVariable("email") String email){
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @Operation(summary = "This endpoint is responsible for validate an user by their email",
            method = "POST",
            description = "Note: this endpoint is only used in OpenFeign internal connections for authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping("/validate/{email}")
    public ResponseEntity<Void> validateUser(@PathVariable("email") String email){
        service.validateUser(email);
        return ResponseEntity.ok().build();
    }
}
