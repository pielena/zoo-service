package com.services.authservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.services.authservice.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(15, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("auth-service")
                .withAudience("animal-service")
                .withSubject(username)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
    }
}
