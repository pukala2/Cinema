package com.cinema.reservation.response;

import com.cinema.reservation.entity.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReservationRequest {
    private int roomNumber;
    private int seatsNumber;
    private String movieTitle;
    Client seats;
}
