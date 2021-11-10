package com.cinema.reservation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRequest {
    private int roomNumber;
    private int seatsNumber;
}
