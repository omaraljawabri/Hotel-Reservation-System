package com.omar.hotel_reservation.clients;

import com.omar.hotel_reservation.dtos.response.UserResponseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(
        name = "authentication-service",
        contextId = "user-service",
        url = "http://localhost:8080/api/v1/user"
)
@Headers("Authorization: {token}")
public interface UserClient {

    @GetMapping("/{id}")
    Optional<UserResponseDTO> findUserById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/exists/{email}")
    boolean userExistsByEmail(@PathVariable("email") String email);

    @PostMapping("/validate/{email}")
    void validateUser(@PathVariable("email") String email);

}
