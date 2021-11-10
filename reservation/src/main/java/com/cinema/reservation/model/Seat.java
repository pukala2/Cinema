package com.cinema.reservation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Seat {
    private Long id;
    private int seatNumber;
    private boolean isBocked;
    private int roomNumber;
    //private Reservation room;
}
