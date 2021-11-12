package com.cinema.reservation.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SeatResponse {
    private Integer seatNumber;
    private Boolean isBocked;
    private Integer roomNumber;
}
