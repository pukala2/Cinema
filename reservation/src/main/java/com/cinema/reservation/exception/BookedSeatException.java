package com.cinema.reservation.exception;

import org.springframework.http.HttpStatus;

public class BookedSeatException extends RuntimeException {

    final private HttpStatus httpStatus;

    public BookedSeatException() {
        super("Seat from request is already booked");
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
