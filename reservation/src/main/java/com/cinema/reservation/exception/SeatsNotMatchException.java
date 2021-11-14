package com.cinema.reservation.exception;

import org.springframework.http.HttpStatus;

public class SeatsNotMatchException extends RuntimeException {

    final private HttpStatus httpStatus;

    public SeatsNotMatchException() {
        super("Seats from request does not match to seats from room");
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
