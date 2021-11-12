package com.cinema.rooms.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRoomRequest {
    private Integer roomNumber;
    private Integer seatsNumber;
    private List<CreateSeatRequest> seats;
}
