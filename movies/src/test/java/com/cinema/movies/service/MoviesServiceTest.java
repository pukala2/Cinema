package com.cinema.movies.service;

import com.cinema.movies.entity.Movie;
import com.cinema.movies.request.UpdateMovieRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoviesServiceTest {

    @InjectMocks
    MoviesService sut;

    @Test
    void shouldUpdateMovie() {

        Movie movie = new Movie();
        movie.setTitle("Killer");
        movie.setCategory("Comedy");

        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest();
        updateMovieRequest.setTitle("Anabel");
        updateMovieRequest.setCategory("Horror");

        sut.updateMovie(updateMovieRequest, movie);

        Assertions.assertEquals(updateMovieRequest.getTitle(), movie.getTitle());
        Assertions.assertEquals(updateMovieRequest.getCategory(), movie.getCategory());
    }

    @Test
    void shouldUpdateTitleMovie() {

        Movie movie = new Movie();
        movie.setTitle("Killer");
        movie.setCategory("Comedy");

        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest();
        updateMovieRequest.setTitle("Mr Bean");

        sut.updateMovie(updateMovieRequest, movie);

        Assertions.assertEquals(updateMovieRequest.getTitle(), movie.getTitle());
        Assertions.assertEquals("Comedy", movie.getCategory());
    }

    @Test
    void shouldUpdateCategoryMovie() {

        Movie movie = new Movie();
        movie.setTitle("Killer");
        movie.setCategory("Comedy");

        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest();
        updateMovieRequest.setCategory("Horror");

        sut.updateMovie(updateMovieRequest, movie);

        Assertions.assertEquals("Killer", movie.getTitle());
        Assertions.assertEquals(updateMovieRequest.getCategory(), movie.getCategory());
    }

    @Test
    void shouldNotUpdateMovie() {

        Movie movie = new Movie();
        movie.setTitle("Killer");
        movie.setCategory("Comedy");

        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest();

        sut.updateMovie(updateMovieRequest, movie);

        Assertions.assertEquals("Killer", movie.getTitle());
        Assertions.assertEquals("Comedy", movie.getCategory());
    }
}