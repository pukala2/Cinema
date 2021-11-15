package com.cinema.reservation.client.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class SeatResponse {
    private Integer seatNumber;
    private Boolean isBocked;
    private Integer roomNumber;
}
