package com.cinema.reservation.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSeatRequest {

    private int seatNumber;

    private boolean isBocked;

    private int roomNumber;
}
