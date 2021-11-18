package com.cinema.movies.repository;

import com.cinema.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
    Optional<Movie> findById(Integer id);
}
