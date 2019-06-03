package com.tasks.bnn.api.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({RestClientException.class})
    public void handleRestClientException(
            HttpServletResponse res,
            Exception e
    ) throws IOException {
        res.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error requesting Microsoft services (" + e.getMessage() + ")"
        );
    }
}
