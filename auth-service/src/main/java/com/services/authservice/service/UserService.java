package com.services.authservice.service;

public interface UserService {

    void register(String clientId, String clientSecret);
    void checkCredentials(String clientId, String clientSecret);
}
