package com.cinema.reservation.client;

import com.cinema.reservation.client.model.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "movies", path = "movies/")
public interface MoviesFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "getByTitle/{title}", consumes = "application/json")
    ResponseEntity<MovieResponse> getByTitle(@PathVariable String title);
}
