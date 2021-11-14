package com.cinema.reservation.client.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UpdateSeatRequest {

    private Integer seatNumber;

    private Boolean isBocked;

    private Integer roomNumber;
}
