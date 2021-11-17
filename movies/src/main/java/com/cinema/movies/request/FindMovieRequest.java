package com.cinema.movies.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindMovieRequest {
    @NotNull(message = "title can not be null")
    @NotEmpty(message = "title can not be empty")
    private String title;
}
