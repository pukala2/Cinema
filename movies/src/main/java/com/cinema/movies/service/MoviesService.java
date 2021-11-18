package com.cinema.movies.service;

import com.cinema.movies.entity.Movie;
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

    public MovieResponse addMovie(Movie movie) {
        moviesRepository.save(movie);
        return new MovieResponse(moviesRepository.save(movie));
    }

    public Movie save(Movie movie) {
        return moviesRepository.save(movie);
    }

    public MovieResponse addMovie(final CreateMovieRequest createMovieRequest) {
        return new MovieResponse(moviesRepository.save(Movie.builder()
                .category(createMovieRequest.getCategory())
                .title(createMovieRequest.getTitle()).build()));
    }

    public Optional<MovieResponse> findByTitle(String title) {
        return moviesRepository.findByTitle(title).map(MovieResponse::new);
    }

    public Optional<Movie> updateMovie(UpdateMovieRequest updateMovieRequest) {
        return moviesRepository.findById(updateMovieRequest.getId()).map(m -> updateMovie(m, updateMovieRequest));
    }

    private Movie updateMovie(Movie movie, UpdateMovieRequest updateMovieRequest) {
        movie.setTitle(updateMovieRequest.getTitle());
        movie.setCategory(updateMovieRequest.getCategory());
        return save(movie);
    }

    public boolean deleteMovie(Long id) {
        return moviesRepository.findById(id).map(m -> {
            moviesRepository.delete(m);
            return true;
        }).orElse(false);
    }
}
