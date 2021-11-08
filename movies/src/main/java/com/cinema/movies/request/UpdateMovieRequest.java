package com.cinema.movies.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateMovieRequest {

    @NotNull(message = "Movies Id is required")
    private Long id;

    private String title;

    private String category;
}
