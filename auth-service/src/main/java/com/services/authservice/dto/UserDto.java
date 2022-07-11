package com.services.authservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String username;
    private String userSecret;
    private int failedAttempt;
    private boolean accountNonLocked;
    private LocalDateTime lockTime;
}
