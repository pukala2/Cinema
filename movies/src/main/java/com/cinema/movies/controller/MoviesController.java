package com.cinema.movies.controller;

import com.cinema.movies.entity.Movie;
import com.cinema.movies.request.CreateMovieRequest;
import com.cinema.movies.request.UpdateMovieRequest;
import com.cinema.movies.response.MovieResponse;
import com.cinema.movies.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies/")
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    @GetMapping("getAll")
    public List<MovieResponse> getAllMovies() {
        List<MovieResponse> movieResponses = new ArrayList<>();
        moviesService.getAllMovies().forEach(movie -> movieResponses.add(new MovieResponse(movie)));
        return movieResponses;
    }

    @PostMapping("addMovie")
    public MovieResponse addMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {

        Movie movie = new Movie();
        movie.setTitle(createMovieRequest.getTitle());
        movie.setCategory(createMovieRequest.getCategory());

        return new MovieResponse(moviesService.saveMovie(movie));
    }

    @PutMapping("updateMovie")
    public MovieResponse updateMovie(@Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
        Optional<Movie> movie = moviesService.findById(updateMovieRequest.getId());
        if (movie.isEmpty()) {
            return null;
        }
        moviesService.updateMovie(updateMovieRequest, movie.get());
        moviesService.saveMovie(movie.get());
        return new MovieResponse(movie.get());
    }

    @DeleteMapping("deleteMovie")
    public String deleteMovie(long id) {
        return moviesService.deleteMovie(id);
    }
}
