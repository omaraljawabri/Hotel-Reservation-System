package com.omar.hotel_reservation.services;

import com.omar.hotel_reservation.clients.AuthClient;
import com.omar.hotel_reservation.clients.UserClient;
import com.omar.hotel_reservation.dtos.request.LoginRequestDTO;
import com.omar.hotel_reservation.dtos.request.RegisterRequestDTO;
import com.omar.hotel_reservation.utils.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemTokenService {

    private final AuthClient authClient;
    private final UserClient userClient;

    @Value("${system.token.email}")
    private String email;

    @Value("${system.token.password}")
    private String password;

    private String cachedToken;
    private long tokenExpiryTime;

    public String generateToken() {
        if (cachedToken == null || System.currentTimeMillis() > tokenExpiryTime) {
            if (!userClient.userExistsByEmail(email)) {
                authClient.registerUser(new RegisterRequestDTO("Admin", "Admin", email, password, UserRole.ADMIN));
                userClient.validateUser(email);
            }

            var loginResponse = authClient.loginUser(new LoginRequestDTO(email, password));
            cachedToken = loginResponse.token();
            tokenExpiryTime = System.currentTimeMillis() + 3600 * 2000;
        }
        return "Bearer "+cachedToken;
    }
}
