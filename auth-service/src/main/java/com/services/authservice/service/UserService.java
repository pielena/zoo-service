package com.services.authservice.service;

public interface UserService {

    void register(String username, String clientSecret);

    void checkCredentials(String username, String clientSecret);
}
