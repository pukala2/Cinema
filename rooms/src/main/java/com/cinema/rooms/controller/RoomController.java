package com.cinema.rooms.controller;

import com.cinema.rooms.config.RoomServiceConfig;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import com.cinema.rooms.response.RoomResponse;
import com.cinema.rooms.response.SeatResponse;
import com.cinema.rooms.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoomController {

    final private RoomService roomService;
    final private RoomServiceConfig roomServiceConfig;

    public RoomController(RoomService roomService, RoomServiceConfig roomServiceConfig) {
        this.roomService = roomService;
        this.roomServiceConfig = roomServiceConfig;
    }

    @GetMapping("/rooms/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        return roomServiceConfig.getPropertyDetails();
    }

    @GetMapping("/rooms/getAll")
    public ResponseEntity<List<RoomResponse>> getAll() {
        return new  ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/rooms/create")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest) {
        return new ResponseEntity<>(roomService.createRoom(createRoomRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/rooms/delete")
    public void deleteRoom(@RequestParam Integer roomNumber) {
        roomService.deleteRoom(roomNumber);
    }

    @GetMapping("/rooms/getRoomByRoomNumber")
    public ResponseEntity<RoomResponse> getRoomByRoomNumber(Integer roomNumber) {
        return new ResponseEntity<>(roomService.getRoomByRoomNumber(roomNumber), HttpStatus.OK);
    }

    @PutMapping("/rooms/changeSeatReservation")
    public ResponseEntity<SeatResponse> changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest) {
        return new ResponseEntity<>(roomService.changeSeatStatus(updateSeatRequest), HttpStatus.OK);
    }
}
