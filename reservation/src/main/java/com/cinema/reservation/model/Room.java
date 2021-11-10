package com.cinema.reservation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Room {
    private Long id;
    private int roomNumber;
    private int seatsNumber;
    List<Seat> seats;
}
