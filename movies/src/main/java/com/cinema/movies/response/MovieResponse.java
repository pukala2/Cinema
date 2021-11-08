package com.cinema.movies.response;

import com.cinema.movies.entity.Movie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {

    private Long id;

    private String title;

    private String category;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.category = movie.getCategory();
    }
}
