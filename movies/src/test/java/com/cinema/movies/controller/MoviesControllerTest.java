package com.cinema.movies.controller;

import com.cinema.movies.repository.MoviesRepository;
import com.cinema.movies.request.CreateMovieRequest;
import com.cinema.movies.request.FindMovieRequest;
import com.cinema.movies.request.UpdateMovieRequest;
import com.cinema.movies.response.MovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MoviesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MoviesRepository moviesRepository;

    @BeforeEach
    void cleanUp() {
        moviesRepository.deleteAll();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    private final static String TITLE = "EASY LIVE IN JAVA";
    private final static String CATEGORY = "DRAMA";

    @Test
    void shouldAddMovie() throws Exception {
        mockMvc.perform(post("/movie")
                        .content(objectMapper.writeValueAsBytes(CreateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var movies = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        Assertions.assertEquals(1, movies.size());
        Assertions.assertEquals(TITLE, movies.get(0).getTitle());
        Assertions.assertEquals(CATEGORY, movies.get(0).getCategory());
    }

    @Test
    void shouldUpdatedMovieCategory() throws Exception {
        mockMvc.perform(post("/movie")
                        .content(objectMapper.writeValueAsBytes(CreateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        final String comedy = "COMEDY";

        MvcResult mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var movies = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        mockMvc.perform(put("/movie")
                        .content(objectMapper.writeValueAsBytes(UpdateMovieRequest.builder()
                                .title(TITLE).category(comedy).id(movies.get(0).getId()).build()))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var result = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(TITLE, result.get(0).getTitle());
        Assertions.assertEquals(comedy, result.get(0).getCategory());
    }

    @Test
    void shouldUpdatedMovieCategoryAndTitle() throws Exception {
        mockMvc.perform(post("/movie")
                        .content(objectMapper.writeValueAsBytes(CreateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        final String comedy = "COMEDY";
        final String title = "MR BEAN";

        MvcResult mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var movies = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        mockMvc.perform(put("/movie")
                        .content(objectMapper.writeValueAsBytes(UpdateMovieRequest.builder()
                                .title(title).category(comedy).id(movies.get(0).getId()).build()))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var result = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(title, result.get(0).getTitle());
        Assertions.assertEquals(comedy, result.get(0).getCategory());
    }

    @Test
    void shouldGetNotFoundExceptionWhenUpdate() throws Exception {
        mockMvc.perform(put("/movie")
                        .content(objectMapper.writeValueAsBytes(UpdateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).id(321L).build()))
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletedMovie() throws Exception {
        mockMvc.perform(post("/movie")
                        .content(objectMapper.writeValueAsBytes(CreateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var movies = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        mockMvc.perform(delete("/movie/deleteMovie/" + movies.get(0).getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        mvcResult = mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andReturn();

        var result = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse[].class));

        Assertions.assertEquals(0, result.size());
    }

    @Test
    void shouldFindMovieByTitle() throws Exception {
        mockMvc.perform(post("/movie")
                        .content(objectMapper.writeValueAsBytes(CreateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/movie/getByTitle/" + TITLE)
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        var movie = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieResponse.class);
        
        Assertions.assertEquals(TITLE, movie.getTitle());
        Assertions.assertEquals(CATEGORY, movie.getCategory());
    }

    @Test
    void shouldNotFindMovieByTitle() throws Exception {
        mockMvc.perform(post("/movie")
                        .content(objectMapper.writeValueAsBytes(CreateMovieRequest.builder()
                                .title(TITLE).category(CATEGORY).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/movie/getByTitle/" + "NOT EXIST")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}