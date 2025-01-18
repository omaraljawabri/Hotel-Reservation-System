package com.omar.hotel_reservation.repositories;

import com.omar.hotel_reservation.entities.ChangePassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangePasswordRepository extends JpaRepository<ChangePassword, Long> {
    Optional<ChangePassword> findByVerificationCode(String code);
}
