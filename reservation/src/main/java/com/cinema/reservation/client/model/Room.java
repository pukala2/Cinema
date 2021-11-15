package com.cinema.reservation.client.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Room {
    private Integer roomNumber;
    private Integer seatsNumber;
    List<SeatResponse> seats;
}
