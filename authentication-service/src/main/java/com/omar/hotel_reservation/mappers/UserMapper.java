package com.omar.hotel_reservation.mappers;

import com.omar.hotel_reservation.dtos.request.RegisterRequestDTO;
import com.omar.hotel_reservation.entities.User;
import com.omar.hotel_reservation.entities.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(RegisterRequestDTO registerRequestDTO){
        return User.builder()
                .firstName(registerRequestDTO.firstName())
                .lastName(registerRequestDTO.lastName())
                .email(registerRequestDTO.email())
                .role(registerRequestDTO.role())
                .isVerified(false)
                .build();
    }
}
