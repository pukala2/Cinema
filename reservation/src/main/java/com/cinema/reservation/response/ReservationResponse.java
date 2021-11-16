package com.cinema.reservation.response;

import com.cinema.reservation.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationResponse {

    @JsonIgnore
    private Long id;

    private Integer roomNumber;

    private Integer seatNumber;

    private String movieTitle;

    private String reservationCode;

    private ClientResponse client;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.roomNumber = reservation.getRoomNumber();
        this.seatNumber = reservation.getSeatNumber();
        this.client = new ClientResponse(reservation.getClient());
        this.movieTitle = reservation.getMovieTitle();
        this.reservationCode = reservation.getReservationCode();
    }
}