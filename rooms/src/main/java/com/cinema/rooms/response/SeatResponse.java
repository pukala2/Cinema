package com.cinema.rooms.response;

import com.cinema.rooms.entity.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatResponse {

    @JsonIgnore
    private Long id;

    private Integer seatNumber;

    private Boolean isBocked;

    private Integer roomNumber;

    public SeatResponse(Seat seat) {
        this.id = seat.getId();
        this.isBocked = seat.getIsBocked();
        this.seatNumber = seat.getSeatNumber();
        this.roomNumber = seat.getRoomNumber();
    }
}
