package com.cinema.rooms.controller;


import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import com.cinema.rooms.response.RoomResponse;
import com.cinema.rooms.response.SeatResponse;
import com.cinema.rooms.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/room")
@RestController
public class RoomController {

    final private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<List<RoomResponse>> getAll() {
        return new ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest) {
        return new ResponseEntity<>(roomService.createRoom(createRoomRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{roomNumber}")
    public ResponseEntity<RoomResponse> deleteRoomByRoomNumber(@PathVariable Integer roomNumber) {
        return roomService.deleteRoom(roomNumber) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getRoomByRoomNumber/{roomNumber}")
    public ResponseEntity<RoomResponse> getRoomByRoomNumber(@PathVariable Integer roomNumber) {
        return roomService.getRoomByRoomNumber(roomNumber).map(room -> new ResponseEntity<>(room, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/changeSeatReservation")
    public ResponseEntity<SeatResponse> changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest) {
        return roomService.changeSeatStatus(updateSeatRequest).map(s -> new ResponseEntity<>(new SeatResponse(s), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
