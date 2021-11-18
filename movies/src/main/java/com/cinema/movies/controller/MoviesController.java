package com.cinema.movies.controller;

import com.cinema.movies.config.MoviesServiceConfig;
import com.cinema.movies.entity.Movie;
import com.cinema.movies.request.CreateMovieRequest;
import com.cinema.movies.request.UpdateMovieRequest;
import com.cinema.movies.response.MovieResponse;
import com.cinema.movies.service.MoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// wyjatek not found bez sensu, ma byc optional
// jezeli jeden  param przekazuje path variable
// wyjebac nazwy w oczywistych metodach restowych


@RequestMapping("/movie")
@RestController
public class MoviesController {

    final private MoviesService moviesService;

    public MoviesController(MoviesService moviesService, MoviesServiceConfig moviesServiceConfig) {
        this.moviesService = moviesService;
    }

    @GetMapping()
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return new ResponseEntity<>(moviesService.getAllMovies(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        return new ResponseEntity<>(moviesService.addMovie(createMovieRequest), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MovieResponse> updateMovie(@Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
        return moviesService.updateMovie(updateMovieRequest).map(m -> new ResponseEntity<>(new MovieResponse(m), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<MovieResponse> deleteMovie(@PathVariable Long id) {
        return moviesService.deleteMovie(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<MovieResponse> getByTitle(@PathVariable String title) {
        return moviesService.findByTitle(title).map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
