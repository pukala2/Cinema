package com.cinema.movies.repository;

import com.cinema.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}
