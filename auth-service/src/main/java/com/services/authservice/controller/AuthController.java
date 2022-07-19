package com.services.authservice.controller;

import com.services.authservice.dto.ErrorResponse;
import com.services.authservice.dto.TokenResponse;
import com.services.authservice.dto.UserDto;
import com.services.authservice.exception.LoginException;
import com.services.authservice.exception.RegistrationException;
import com.services.authservice.service.TokenService;
import com.services.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.register(userDto.getUsername(), userDto.getUserSecret());
        return ResponseEntity.ok("Successful registered. Go ahead!");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody UserDto userDto) {
        userService.checkCredentials(userDto.getUsername(), userDto.getUserSecret());
        return new TokenResponse(tokenService.generateToken(userDto.getUsername()));
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}
