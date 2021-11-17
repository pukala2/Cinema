package com.cinema.movies.service;

import com.cinema.movies.entity.Movie;
import com.cinema.movies.exception.NotExistingMovieException;
import com.cinema.movies.repository.MoviesRepository;
import com.cinema.movies.request.CreateMovieRequest;
import com.cinema.movies.request.UpdateMovieRequest;
import com.cinema.movies.response.MovieResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    final private MoviesRepository moviesRepository;

    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<MovieResponse> getAllMovies() {
        List<MovieResponse> movieResponses = new ArrayList<>();
        moviesRepository.findAll().forEach(movie -> movieResponses.add(new MovieResponse(movie)));
        return movieResponses;
    }

    public MovieResponse updateMovie(UpdateMovieRequest updateMovieRequest) {
        Optional<Movie> movie = moviesRepository.findById(updateMovieRequest.getId());
        if (movie.isEmpty()) {
            throw new NotExistingMovieException();
        }
        updateMovie(updateMovieRequest, movie.get());
        return addMovie(movie.get());
    }

    public MovieResponse addMovie(Movie movie) {
        moviesRepository.save(movie);
        return new MovieResponse(moviesRepository.save(movie));
    }

    private void updateMovie(final UpdateMovieRequest updateMovieRequest, Movie movie) {
        if (updateMovieRequest.getTitle() != null && !updateMovieRequest.getTitle().isEmpty()) {
            movie.setTitle(updateMovieRequest.getTitle());
        }
        if (updateMovieRequest.getCategory() != null && !updateMovieRequest.getCategory().isEmpty()) {
            movie.setCategory(updateMovieRequest.getCategory());
        }
    }

    public MovieResponse addMovie(final CreateMovieRequest createMovieRequest) {
        return new MovieResponse(moviesRepository.save(Movie.builder()
                .category(createMovieRequest.getCategory())
                .title(createMovieRequest.getTitle()).build()));
    }

    public void deleteMovie(Long id) {
        moviesRepository.deleteById(id);
    }

    public MovieResponse findByTitle(String title) {

        Movie movie = moviesRepository.findByTitle(title);
        if (movie == null) {
            throw new NotExistingMovieException();
        }
        return new MovieResponse(movie);
    }
}
