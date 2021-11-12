package com.cinema.rooms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSeatRequest {
    private Integer seatNumber;
    private Boolean isBocked;
    private Integer roomNumber;
}
