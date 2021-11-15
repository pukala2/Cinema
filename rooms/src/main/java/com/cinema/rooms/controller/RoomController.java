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
@RequestMapping("/rooms/")
public class RoomController {

    final private RoomService roomService;
    final private RoomServiceConfig roomServiceConfig;

    public RoomController(RoomService roomService, RoomServiceConfig roomServiceConfig) {
        this.roomService = roomService;
        this.roomServiceConfig = roomServiceConfig;
    }

    @GetMapping("properties")
    public String getPropertyDetails() throws JsonProcessingException {
        return roomServiceConfig.getPropertyDetails();
    }

    @GetMapping("getAll")
    public List<RoomResponse> getAll() {
        return roomService.getAll();
    }

    @PostMapping("create")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest) {
        return new ResponseEntity<>(roomService.createRoom(createRoomRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("delete")
    public void deleteRoom(@RequestParam Integer roomNumber) {
        roomService.deleteRoom(roomNumber);
    }

    @GetMapping("getRoomByRoomNumber")
    public RoomResponse getRoomByRoomNumber(Integer roomNumber) {
        return roomService.getRoomByRoomNumber(roomNumber);
    }

    @PutMapping("changeSeatReservation")
    public SeatResponse changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest) {
        return roomService.changeSeatStatus(updateSeatRequest);
    }
}
