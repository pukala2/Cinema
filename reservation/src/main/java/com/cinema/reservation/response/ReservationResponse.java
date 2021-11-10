package com.cinema.reservation.response;

import com.cinema.reservation.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationResponse {

    @JsonIgnore
    private Long id;

    private int roomNumber;

    private int seatsNumber;

    private ClientResponse client;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.roomNumber = reservation.getRoomNumber();
        this.seatsNumber = reservation.getSeatsNumber();
        this.client = new ClientResponse(reservation.getClient());
    }
}