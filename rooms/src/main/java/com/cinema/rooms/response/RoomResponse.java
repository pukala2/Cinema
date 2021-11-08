package com.cinema.rooms.response;

import com.cinema.rooms.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoomResponse {

    private Long id;

    private int roomNumber;

    private int seatsNumber;

    private List<SeatResponse> seats;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.seatsNumber = room.getSeatsNumber();

        if (room.getSeats() != null) {
            seats = new ArrayList<>();
            room.getSeats().forEach(seat -> seats.add(new SeatResponse(seat)));
        }
    }
}
