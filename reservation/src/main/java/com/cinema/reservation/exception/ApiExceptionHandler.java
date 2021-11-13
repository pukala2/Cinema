package com.cinema.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({NotExistingReservationCodeException.class, BockedSeatException.class})
    public ResponseEntity<ApiException> handleApiRequestException(NotExistingReservationCodeException e) {
        final ApiException exception = new ApiException(
                e.getMessage(),
                e.getHttpStatus(),
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, e.getHttpStatus());
    }

    @ExceptionHandler(value = BockedSeatException.class)
    public ResponseEntity<ApiException> handleApiRequestException(BockedSeatException e) {
        final ApiException exception = new ApiException(
                e.getMessage(),
                e.getHttpStatus(),
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(exception, e.getHttpStatus());
    }
}
