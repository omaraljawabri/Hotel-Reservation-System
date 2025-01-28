package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.ChangePasswordRequestDTO;
import com.omar.hotel_reservation.dtos.request.LoginRequestDTO;
import com.omar.hotel_reservation.dtos.request.NewPasswordRequestDTO;
import com.omar.hotel_reservation.dtos.request.RegisterRequestDTO;
import com.omar.hotel_reservation.dtos.response.LoginResponseDTO;
import com.omar.hotel_reservation.entities.User;
import com.omar.hotel_reservation.infra.handler.ErrorMessage;
import com.omar.hotel_reservation.infra.security.TokenService;
import com.omar.hotel_reservation.services.ChangePasswordService;
import com.omar.hotel_reservation.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "These endpoints are responsible for register and login user, verify new registers and change password requests and change user password")
public class AuthController {

    private final UserService userService;
    private final ChangePasswordService changePasswordService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Operation(summary = "This endpoint is responsible for register an user",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        userService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is responsible for verify a new register",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @GetMapping("/verify")
    public ResponseEntity<Void> verifyRegistrationCode(@RequestParam("code") String code){
        userService.verifyRegistrationCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "This endpoint is responsible for login an user",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(userService.login(loginRequestDTO, token));
    }

    @Operation(summary = "This endpoint is responsible for send an email request to change user password",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(value = "/change-password/request", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> requestChangePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        changePasswordService.requestChangePassword(changePasswordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "This endpoint is responsible for verify and change user password",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error during operation(Internal server error)")
    })
    @PostMapping(value = "/change-password/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> verifyCodeAndChangePassword(@RequestParam("code") String code,
                                                            @RequestBody NewPasswordRequestDTO newPasswordRequestDTO){
        changePasswordService.verifyCodeAndChangePassword(code, newPasswordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
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
