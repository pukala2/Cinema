package com.cinema.reservation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
