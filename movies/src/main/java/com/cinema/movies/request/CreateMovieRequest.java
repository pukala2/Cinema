package com.cinema.movies.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieRequest {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "category is required")
    private String category;
}
