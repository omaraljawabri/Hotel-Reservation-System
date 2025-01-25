package com.omar.hotel_reservation.controllers;

import com.omar.hotel_reservation.dtos.response.UserResponseDTO;
import com.omar.hotel_reservation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsById(@PathVariable("email") String email){
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @PostMapping("/validate/{email}")
    public ResponseEntity<Void> validateUser(@PathVariable("email") String email){
        service.validateUser(email);
        return ResponseEntity.ok().build();
    }
}
