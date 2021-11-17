package com.cinema.movies.controller;

import com.cinema.movies.config.MoviesServiceConfig;
import com.cinema.movies.request.CreateMovieRequest;
import com.cinema.movies.request.FindMovieRequest;
import com.cinema.movies.request.UpdateMovieRequest;
import com.cinema.movies.response.MovieResponse;
import com.cinema.movies.service.MoviesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MoviesController {

    final private MoviesService moviesService;
    final private MoviesServiceConfig moviesServiceConfig;

    public MoviesController(MoviesService moviesService, MoviesServiceConfig moviesServiceConfig) {
        this.moviesService = moviesService;
        this.moviesServiceConfig = moviesServiceConfig;
    }

    @GetMapping("/movies/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        return moviesServiceConfig.getPropertyDetails();
    }

    @GetMapping("/movies/getAll")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return new ResponseEntity<>(moviesService.getAllMovies(), HttpStatus.OK);
    }

    @PostMapping("/movies/addMovie")
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        return new ResponseEntity<>(moviesService.addMovie(createMovieRequest), HttpStatus.CREATED);
    }

    @PutMapping("/movies/updateMovie")
    public  ResponseEntity<MovieResponse> updateMovie(@Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
        return new  ResponseEntity<>(moviesService.updateMovie(updateMovieRequest), HttpStatus.OK);
    }

    @DeleteMapping("/movies/deleteMovie")
    public void deleteMovie(Long id) {
        moviesService.deleteMovie(id);
    }

    @GetMapping("/movies/getByTitle")
    public ResponseEntity<MovieResponse> getByTitle(@Valid @RequestBody FindMovieRequest findMovieRequest) {
        return new ResponseEntity<>(moviesService.findByTitle(findMovieRequest.getTitle()), HttpStatus.OK);
    }
}
