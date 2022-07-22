package com.services.animalservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int errorCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;
    private String debugMessage;

    static final ApiError USERNAME_NOT_FOUND = new ApiError(6, "Username not found");
    static final ApiError ANIMAL_BAD_REQUEST = new ApiError(8, "Incorrect request");
    static final ApiError MESSAGE_NOT_READABLE = new ApiError(9, "Malformed JSON Request");
    static final ApiError UNKNOWN_ERROR = new ApiError(7, "Something went wrong");
    static final ApiError METHOD_ARGUMENT_TYPE_MISMATCH = new ApiError(10, "Argument type mismatch");
    static final ApiError NO_HANDLER_FOUND = new ApiError(11, "No handler found");

    public ApiError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
