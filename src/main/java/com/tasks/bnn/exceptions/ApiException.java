package com.tasks.bnn.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException extends Exception {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private int code;

    private String error;

    private String message;

    private ApiException() {
        timestamp = LocalDateTime.now();
    }

    ApiException(HttpStatus status) {
        this();
        this.code = status.value();
    }

    ApiException(HttpStatus status, String message) {
        this();
        this.code = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }
}
