package com.cinema.rooms.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateRoomRequest {

    @NotNull
    private Integer roomNumber;
    private Integer seatsNumber;
}
