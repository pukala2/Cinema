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

    private int seatNumber;

    private boolean isBocked;

    private int roomNumber;

    public SeatResponse(Seat seat) {
        this.id = seat.getId();
        this.isBocked = seat.isBocked();
        this.seatNumber = seat.getSeatNumber();
        this.roomNumber = seat.getRoomNumber();
    }
}
