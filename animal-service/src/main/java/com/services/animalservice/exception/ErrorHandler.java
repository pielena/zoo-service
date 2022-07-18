package com.services.animalservice.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<Object> handleStatusException(ResponseStatusException ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getReason(), ex.getMessage());
        apiError.setStatus(ex.getStatus());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(ex.getParameterName() + " parameter is missing", ex.getMessage());
        apiError.setStatus(BAD_REQUEST);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        message.append(ex.getContentType());
        message.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> message.append(t).append(", "));
        ApiError apiError = new ApiError(message.substring(0, message.length() - 2), ex.getMessage());
        apiError.setStatus(UNSUPPORTED_MEDIA_TYPE);
        return new ResponseEntity<>(apiError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(EntityNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError("Entity Not Found Exception", ex.getMessage());
        apiError.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError("Malformed JSON Request", ex.getMessage());
        apiError.setStatus(BAD_REQUEST);
        return new ResponseEntity<>(apiError, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError("Error writing JSON output", ex.getMessage());
        apiError.setStatus(status);
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()), ex.getMessage());
        apiError.setStatus(status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            ApiError apiError = new ApiError("Database error", ex.getMessage());
            apiError.setStatus(CONFLICT);
            return new ResponseEntity<>(apiError, CONFLICT);
        }
        ApiError apiError = new ApiError("Database error", ex.getMessage());
        apiError.setStatus(INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        apiError.setStatus(BAD_REQUEST);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError("Internal Exception", ex.getMessage());
        apiError.setStatus(INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
