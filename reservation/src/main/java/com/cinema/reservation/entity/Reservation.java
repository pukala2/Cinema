package com.cinema.reservation.entity;

import com.cinema.reservation.request.CreateReservationRequest;
import lombok.Builder;
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
    private Integer roomNumber;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "reservation_code")
    private String reservationCode;

    @OneToOne(mappedBy = "reservation")
    Client client;
}
