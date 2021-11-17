package com.cinema.movies.exception;

import org.springframework.http.HttpStatus;

public class NotExistingMovieException extends RuntimeException {

    final private HttpStatus httpStatus;

    public NotExistingMovieException() {
        super("Movie does not exist");
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
