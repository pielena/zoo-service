package com.services.authservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int errorCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    static final ApiError INVALID_USERNAME_PASSWORD = new ApiError(1, "Invalid username/password supplied");
    static final ApiError USERNAME_ALREADY_IN_USE = new ApiError(2, "Username is already in use");
    static final ApiError TOO_MANY_SIGN_ATTEMPTS = new ApiError(3, "Too many sign attempts");
    static final ApiError ARGUMENT_NOT_VALID = new ApiError(4, "Argument Not Valid");
    static final ApiError UNKNOWN_ERROR = new ApiError(7, "Something went wrong");
    static final ApiError MESSAGE_NOT_READABLE = new ApiError(9, "Malformed JSON Request");
    static final ApiError NO_HANDLER_FOUND = new ApiError(11, "No handler found");

    public ApiError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
