package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "authentication-service",
        url = "http://localhost:8080/api/v1/user"
)
public interface UserClient {

    @GetMapping("/{id}")
    Optional<UserResponseDTO> findUserById(@PathVariable("id") Long id);
}
