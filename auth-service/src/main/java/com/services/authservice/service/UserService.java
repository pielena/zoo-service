package com.services.authservice.service;

import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {

    void register(String username, String clientSecret);

    void checkCredentials(String username, String clientSecret);
}
