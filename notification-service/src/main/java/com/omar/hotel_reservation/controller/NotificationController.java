package com.omar.hotel_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @GetMapping("/health")
    public ResponseEntity<Void> verifyServiceHealth(){
        return ResponseEntity.ok().build();
    }
}
