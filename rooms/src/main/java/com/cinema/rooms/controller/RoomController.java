package com.cinema.rooms.controller;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import com.cinema.rooms.response.RoomResponse;
import com.cinema.rooms.response.SeatResponse;
import com.cinema.rooms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rooms/")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("getAll")
    public List<RoomResponse> getAll() {
        List<RoomResponse> roomResponses = new ArrayList<>();
        roomService.getAll().forEach(room -> roomResponses.add(new RoomResponse(room)));
        return roomResponses;
    }

    @PostMapping("create")
    public RoomResponse createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest) {
        Room room = roomService.createRoom(createRoomRequest);
        roomService.saveRoom(room);
        roomService.saveSeats(room.getSeats());
        return new RoomResponse(room);
    }

    @Transactional
    @DeleteMapping("delete")
    public String deleteRoom(@RequestParam int roomNumber) {
        List<Seat> seats = roomService.getSeatsByRoomNumber(roomNumber);
        roomService.removeSeats(seats);
        roomService.removeRoomByRoomNumber(roomNumber);

        return "Room has been deleted successfully";
    }

    @GetMapping("getSeatsFromRoom")
    public List<SeatResponse> getSeatsFromRoom(@RequestParam int roomNumber) {
        List<SeatResponse> seatResponses = new ArrayList<>();
        roomService.getSeatsFromRoom(roomNumber).forEach(seat -> seatResponses.add(new SeatResponse(seat)));

        return seatResponses;
    }

    @PutMapping("changeSeatReservation")
    public String changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest) {

        Optional<Seat> seat = roomService.changeSeatReservation(updateSeatRequest);
        if (seat.isEmpty()) {
            return "The place doest not exist";
        }
        roomService.saveSeat(seat.get());
        return "The seat status has been changed";
    }
}
