package com.services.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@AllArgsConstructor
public class ErrorResponse {

    HttpStatus status;
    String message;
}
