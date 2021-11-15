package com.cinema.reservation.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Room {
    private Integer roomNumber;
    private Integer seatsNumber;
    List<SeatResponse> seats;
}
