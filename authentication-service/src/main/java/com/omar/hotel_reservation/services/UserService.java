package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.dtos.request.LoginRequestDTO;
import com.omar.hotel_reservation.dtos.request.RegisterRequestDTO;
import com.omar.hotel_reservation.dtos.response.LoginResponseDTO;
import com.omar.hotel_reservation.dtos.response.UserResponseDTO;
import com.omar.hotel_reservation.entities.User;
import com.omar.hotel_reservation.exceptions.InvalidCodeException;
import com.omar.hotel_reservation.exceptions.UserAlreadyExistsException;
import com.omar.hotel_reservation.kafka.AuthConfirmation;
import com.omar.hotel_reservation.kafka.ChangePasswordProducer;
import com.omar.hotel_reservation.kafka.RegisterProducer;
import com.omar.hotel_reservation.mappers.UserMapper;
import com.omar.hotel_reservation.repositories.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final RegisterProducer registerProducer;

    @Transactional
    public void registerUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.email()).isPresent()){
            throw new UserAlreadyExistsException("User with email : "+registerRequestDTO.email()+ " already exists");
        }
        String verificationCode = UUID.randomUUID().toString();
        String password = new BCryptPasswordEncoder().encode(registerRequestDTO.password());
        User user = mapper.toUser(registerRequestDTO);
        user.setPassword(password);
        user.setVerificationCode(verificationCode);
        user.setExpirationCodeTime(LocalDateTime.now().plusHours(24));
        registerProducer.sendRegisterConfirmation(new AuthConfirmation(
                String.format("http://localhost:8080/api/v1/auth/verify?code=%s", verificationCode),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        ));
        userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, String token) {
        User user = findByEmail(loginRequestDTO.email());
        if (!user.isVerified()){
            throw new ValidationException("User is not validated");
        }
        return LoginResponseDTO.builder().token(token).email(user.getEmail())
                .build();
    }

    public void verifyRegistrationCode(String code) {
        User user
                = userRepository.findByVerificationCode(code).orElseThrow(() -> new InvalidCodeException("Code passed not found"));
        if (user.getExpirationCodeTime().isBefore(LocalDateTime.now())){
            throw new InvalidCodeException("Code already expired");
        }
        user.setVerified(true);
        user.setVerificationCode(null);
        user.setExpirationCodeTime(null);
        userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: "+email+" not found"));
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id: " + id + " not found"));
        if (!user.isVerified()){
            throw new ValidationException(String.format("User with id: %d is not verified", id));
        }
        return mapper.toUserResponse(user);
    }
}
