package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.request.LoginRequestDTO;
import com.omar.hotel_reservation.dtos.request.RegisterRequestDTO;
import com.omar.hotel_reservation.dtos.response.LoginResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "authentication-service",
        contextId = "auth-service",
        url = "http://localhost:8080/api/v1/auth"
)
public interface AuthClient {

    @PostMapping("/register")
    void registerUser(@RequestBody RegisterRequestDTO registerRequestDTO);

    @PostMapping("/login")
    LoginResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequestDTO);
}
