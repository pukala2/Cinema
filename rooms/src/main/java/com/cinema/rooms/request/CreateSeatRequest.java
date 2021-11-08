package com.cinema.rooms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSeatRequest {

    private int seatNumber;

    private boolean isBocked;

    private int roomNumber;
}
