package com.omar.hotel_reservation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${hotel-reservation.auth.token.secret}")
    private String secret;

    public String validateAndExtractRole(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("hotel-reservation-system")
                    .build()
                    .verify(token);

            return decodedJWT.getClaim("role").asString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid token");
        }
    }
}
