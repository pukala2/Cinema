package com.cinema.reservation.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSeatRequest {
    private Integer roomNumber;
    private Integer seatNumber;
}
