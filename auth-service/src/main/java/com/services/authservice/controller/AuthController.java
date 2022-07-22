package com.services.authservice.controller;

import com.services.authservice.dto.TokenResponse;
import com.services.authservice.dto.UserDto;
import com.services.authservice.service.TokenService;
import com.services.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public TokenResponse register(@Valid @RequestBody UserDto userDto) {
        userService.register(userDto.getUsername(), userDto.getUserSecret());
        return new TokenResponse(tokenService.generateToken(userDto.getUsername()));
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody UserDto userDto) {
        userService.checkCredentials(userDto.getUsername(), userDto.getUserSecret());
        return new TokenResponse(tokenService.generateToken(userDto.getUsername()));
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkUsername(@Valid @RequestBody UserDto userDto) {
        userService.checkUsername(userDto.getUsername());
        return ResponseEntity.ok("This username is free");
    }
}
