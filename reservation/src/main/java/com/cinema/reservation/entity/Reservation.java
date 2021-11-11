package com.cinema.reservation.entity;

import com.cinema.reservation.request.CreateReservationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "seats_number")
    private int seatsNumber;

    @Column(name = "movie_title")
    private String movieTitle;

    @OneToOne(mappedBy = "reservation")
    Client client;
}
