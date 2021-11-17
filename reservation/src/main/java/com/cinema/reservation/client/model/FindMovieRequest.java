package com.cinema.reservation.client.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FindMovieRequest {
    @NotNull(message = "title can not be null")
    @NotEmpty(message = "title can not be empty")
    private String title;
}
