package com.cinema.rooms.exception;

import org.springframework.http.HttpStatus;

public class NotExistingSeatException extends RuntimeException {

    final private HttpStatus httpStatus;

    public NotExistingSeatException() {
        super("Can not find a seat with this number in the room");
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
