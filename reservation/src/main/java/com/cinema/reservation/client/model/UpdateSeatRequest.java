package com.cinema.reservation.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UpdateSeatRequest {

    private Integer seatNumber;

    private Boolean isBocked;

    private Integer roomNumber;
}
