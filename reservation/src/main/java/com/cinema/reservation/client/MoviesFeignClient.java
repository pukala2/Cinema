package com.cinema.reservation.client;

import com.cinema.reservation.client.model.FindMovieRequest;
import com.cinema.reservation.client.model.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "movies", path = "movies/")
public interface MoviesFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "getByTitle", consumes = "application/json")
    MovieResponse getByTitle(@Valid @RequestBody FindMovieRequest findMovieRequest);
}
