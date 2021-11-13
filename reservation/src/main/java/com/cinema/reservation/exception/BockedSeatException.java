package com.cinema.reservation.exception;

import org.springframework.http.HttpStatus;

public class BockedSeatException extends RuntimeException {

    final private HttpStatus httpStatus;

    public BockedSeatException() {
        super("Seat can not be booked");
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
