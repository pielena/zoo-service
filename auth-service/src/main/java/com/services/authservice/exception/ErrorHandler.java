package com.services.authservice.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.services.authservice.exception.ApiError.ARGUMENT_NOT_VALID;
import static com.services.authservice.exception.ApiError.INVALID_USERNAME_PASSWORD;
import static com.services.authservice.exception.ApiError.MESSAGE_NOT_READABLE;
import static com.services.authservice.exception.ApiError.NO_HANDLER_FOUND;
import static com.services.authservice.exception.ApiError.TOO_MANY_SIGN_ATTEMPTS;
import static com.services.authservice.exception.ApiError.UNKNOWN_ERROR;
import static com.services.authservice.exception.ApiError.USERNAME_ALREADY_IN_USE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.LENGTH_REQUIRED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AttemptsLimitException.class)
    ResponseEntity<Object> handleAttemptsLimitException(AttemptsLimitException ex, WebRequest request) {
        TOO_MANY_SIGN_ATTEMPTS.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(TOO_MANY_SIGN_ATTEMPTS, TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(LoginException.class)
    ResponseEntity<Object> handleLoginException(LoginException ex, WebRequest request) {
        INVALID_USERNAME_PASSWORD.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(INVALID_USERNAME_PASSWORD, UNAUTHORIZED);
    }

    @ExceptionHandler(RegistrationException.class)
    ResponseEntity<Object> handleRegistrationException(RegistrationException ex, WebRequest request) {
        USERNAME_ALREADY_IN_USE.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(USERNAME_ALREADY_IN_USE, CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ARGUMENT_NOT_VALID.setErrors(errors);

        return new ResponseEntity<>(ARGUMENT_NOT_VALID, LENGTH_REQUIRED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        MESSAGE_NOT_READABLE.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(MESSAGE_NOT_READABLE, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        NO_HANDLER_FOUND.setDebugMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

        return new ResponseEntity<>(NO_HANDLER_FOUND, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        UNKNOWN_ERROR.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(UNKNOWN_ERROR, INTERNAL_SERVER_ERROR);
    }
}
