package com.services.authservice.exception;

public class AttemptsLimitException extends RuntimeException {

    public AttemptsLimitException(String message) {
        super(message);
    }
}
