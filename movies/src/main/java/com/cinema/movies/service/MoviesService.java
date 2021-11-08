package com.cinema.movies.service;

import com.cinema.movies.entity.Movie;
import com.cinema.movies.repository.MoviesRepository;
import com.cinema.movies.request.CreateMovieRequest;
import com.cinema.movies.request.UpdateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    @Autowired
    private MoviesRepository moviesRepository;

    public List<Movie> getAllMovies() {
        return moviesRepository.findAll();
    }

    public Movie addMovie(final CreateMovieRequest createMovieRequest) {

        Movie movie = new Movie();
        movie.setTitle(createMovieRequest.getTitle());
        movie.setCategory(createMovieRequest.getCategory());

        return moviesRepository.save(movie);
    }

    public Optional<Movie> findById(long id) {
        return moviesRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return moviesRepository.save(movie);
    }

    public void updateMovie(final UpdateMovieRequest updateMovieRequest, Movie movie) {

        if (updateMovieRequest.getTitle() != null && !updateMovieRequest.getTitle().isEmpty()) {
            movie.setTitle(updateMovieRequest.getTitle());
        }
        if (updateMovieRequest.getCategory() != null && !updateMovieRequest.getCategory().isEmpty()) {
            movie.setCategory(updateMovieRequest.getCategory());
        }
    }

    public String deleteMovie(long id) {
        moviesRepository.deleteById(id);
        return "Movie has been deleted successfully";
    }
}
