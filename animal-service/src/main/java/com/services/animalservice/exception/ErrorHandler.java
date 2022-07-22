package com.services.animalservice.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.services.animalservice.exception.ApiError.ANIMAL_BAD_REQUEST;
import static com.services.animalservice.exception.ApiError.MESSAGE_NOT_READABLE;
import static com.services.animalservice.exception.ApiError.METHOD_ARGUMENT_TYPE_MISMATCH;
import static com.services.animalservice.exception.ApiError.NO_HANDLER_FOUND;
import static com.services.animalservice.exception.ApiError.UNKNOWN_ERROR;
import static com.services.animalservice.exception.ApiError.USERNAME_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        USERNAME_NOT_FOUND.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(USERNAME_NOT_FOUND, NOT_FOUND);
    }

    @ExceptionHandler(AnimalServiceException.class)
    ResponseEntity<Object> handleAnimalServiceException(AnimalServiceException ex, WebRequest request) {
        ANIMAL_BAD_REQUEST.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(ANIMAL_BAD_REQUEST, BAD_REQUEST);
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        METHOD_ARGUMENT_TYPE_MISMATCH.setDebugMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));

        return new ResponseEntity<>(METHOD_ARGUMENT_TYPE_MISMATCH, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        UNKNOWN_ERROR.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(UNKNOWN_ERROR, INTERNAL_SERVER_ERROR);
    }
}
