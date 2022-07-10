package com.services.authservice.service;

public interface TokenService {

    String generateToken(String clientId);
}
