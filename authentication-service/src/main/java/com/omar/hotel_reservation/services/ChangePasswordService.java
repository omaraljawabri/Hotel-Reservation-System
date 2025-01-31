package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.dtos.request.ChangePasswordRequestDTO;
import com.omar.hotel_reservation.dtos.request.NewPasswordRequestDTO;
import com.omar.hotel_reservation.entities.ChangePassword;
import com.omar.hotel_reservation.entities.User;
import com.omar.hotel_reservation.exceptions.InvalidCodeException;
import com.omar.hotel_reservation.kafka.AuthConfirmation;
import com.omar.hotel_reservation.kafka.ChangePasswordProducer;
import com.omar.hotel_reservation.kafka.RegisterProducer;
import com.omar.hotel_reservation.repositories.ChangePasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final ChangePasswordRepository changePasswordRepository;
    private final UserService userService;
    private final ChangePasswordProducer changePasswordProducer;

    @Transactional
    public void requestChangePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        User user = userService.findByEmail(changePasswordRequestDTO.email());
        String verificationCode = UUID.randomUUID().toString();
        ChangePassword changePassword = ChangePassword.builder().verificationCode(verificationCode).expirationCodeTime(LocalDateTime.now().plusDays(2))
                .user(user).build();
        changePasswordProducer.sendChangePasswordConfirmation(new AuthConfirmation(
                String.format("http://localhost:8080/api/v1/auth/change-password/verify?code=%s", verificationCode),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        ));
        changePasswordRepository.save(changePassword);
    }

    public void verifyCodeAndChangePassword(String code, NewPasswordRequestDTO newPasswordRequestDTO) {
        ChangePassword changePassword = changePasswordRepository.findByVerificationCode(code)
                .orElseThrow(() -> new InvalidCodeException("Code passed not found"));
        if (changePassword.getExpirationCodeTime().isBefore(LocalDateTime.now())){
            throw new InvalidCodeException("Code already expired");
        }
        User user = changePassword.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPasswordRequestDTO.newPassword()));
        userService.saveUser(user);
        changePasswordRepository.deleteById(changePassword.getId());
    }
}
