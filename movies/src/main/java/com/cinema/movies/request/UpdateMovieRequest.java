package com.cinema.movies.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieRequest {

    @NotNull(message = "Movies Id is required")
    private Long id;

    private String title;

    private String category;
}
