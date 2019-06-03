package com.tasks.bnn.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler {
    @ExceptionHandler({RestClientException.class})
    public void handleRestClientException(
            HttpServletResponse res,
            Exception e
    ) throws IOException {
        log.error("Caught exception: " + e.getMessage());
        res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
