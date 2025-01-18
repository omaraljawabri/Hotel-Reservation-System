package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.request.ChangePasswordRequestDTO;
import com.omar.hotel_reservation.dtos.request.LoginRequestDTO;
import com.omar.hotel_reservation.dtos.request.NewPasswordRequestDTO;
import com.omar.hotel_reservation.dtos.request.RegisterRequestDTO;
import com.omar.hotel_reservation.dtos.response.LoginResponseDTO;
import com.omar.hotel_reservation.entities.User;
import com.omar.hotel_reservation.infra.security.TokenService;
import com.omar.hotel_reservation.services.ChangePasswordService;
import com.omar.hotel_reservation.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ChangePasswordService changePasswordService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        userService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyRegistrationCode(@RequestParam("code") String code){
        userService.verifyRegistrationCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(userService.login(loginRequestDTO, token));
    }

    @PostMapping("/change-password/request")
    public ResponseEntity<Void> requestChangePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        changePasswordService.requestChangePassword(changePasswordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password/verify")
    public ResponseEntity<Void> verifyCodeAndChangePassword(@RequestParam("code") String code,
                                                            @RequestBody NewPasswordRequestDTO newPasswordRequestDTO){
        changePasswordService.verifyCodeAndChangePassword(code, newPasswordRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
