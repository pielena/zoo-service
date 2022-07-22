package com.services.animalservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.services.animalservice.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    public String validateTokenAndRetrieveSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-service")) {
                log.error("Issuer is incorrect");
                throw new InvalidTokenException("Issuer is incorrect");
            }

            if (!decodedJWT.getAudience().contains("animal-service")) {
                log.error("User is incorrect");
                throw new InvalidTokenException("User is incorrect");
            }

            return decodedJWT.getSubject();

        } catch (JWTVerificationException e) {
            log.error("Token is invalid: " + e.getMessage());
            throw new InvalidTokenException("Token is invalid");
        }
    }
}
