package com.cinema.movies.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateMovieRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "category is required")
    private String category;
}
