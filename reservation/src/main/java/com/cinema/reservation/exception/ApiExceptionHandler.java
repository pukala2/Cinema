package com.cinema.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        final ApiException exception = new ApiException(
                e.getMessage(),
                e,
                e.getHttpStatus(),
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, e.getHttpStatus());
    }
}
