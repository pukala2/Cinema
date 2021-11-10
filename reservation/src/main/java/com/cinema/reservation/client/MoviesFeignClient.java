package com.cinema.reservation.client;

import com.cinema.reservation.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("movies")
public interface MoviesFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "getAll", consumes = "application/json")
    List<Movie> getAllMovies();
}