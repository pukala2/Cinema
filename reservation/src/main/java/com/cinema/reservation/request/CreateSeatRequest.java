package com.cinema.reservation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSeatRequest {
    private Integer roomNumber;
    private Integer seatNumber;
}
