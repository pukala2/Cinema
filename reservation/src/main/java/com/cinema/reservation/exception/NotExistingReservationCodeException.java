package com.cinema.reservation.exception;

import org.springframework.http.HttpStatus;

public class NotExistingReservationCodeException extends RuntimeException {

    final private HttpStatus httpStatus;

    public NotExistingReservationCodeException() {
        super("Reservation with this code does not exist");
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
