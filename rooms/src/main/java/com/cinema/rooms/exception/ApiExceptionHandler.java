package com.cinema.rooms.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = NotExistingSeatException.class)
    public ResponseEntity<ApiException> handleApiRequestException(NotExistingSeatException e) {
        final ApiException exception = new ApiException(
                e.getMessage(),
                e.getHttpStatus(),
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, e.getHttpStatus());
    }
}
