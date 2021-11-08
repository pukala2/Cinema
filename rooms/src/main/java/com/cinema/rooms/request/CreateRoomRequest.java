package com.cinema.rooms.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRoomRequest {

    private int roomNumber;

    private int seatsNumber;

    private List<CreateSeatRequest> seats;
}
