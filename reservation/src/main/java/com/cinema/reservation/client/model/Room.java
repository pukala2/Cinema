package com.cinema.reservation.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Room {
    private int roomNumber;
    private int seatsNumber;
    List<SeatResponse> seatResponseRespons;
}
