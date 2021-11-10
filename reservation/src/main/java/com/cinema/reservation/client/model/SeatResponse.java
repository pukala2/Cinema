package com.cinema.reservation.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeatResponse {
    private int seatNumber;
    private boolean isBocked;
    private int roomNumber;
}
