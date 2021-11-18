package com.cinema.rooms.service;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.repository.RoomRepository;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import com.cinema.rooms.response.RoomResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    final private RoomRepository roomRepository;
    final private SeatService seatService;

    public RoomService(RoomRepository roomRepository, SeatService seatService) {
        this.roomRepository = roomRepository;
        this.seatService = seatService;
    }

    public List<RoomResponse> getAll() {
        List<RoomResponse> roomResponses = new ArrayList<>();
        roomRepository.findAll().forEach(room -> roomResponses.add(new RoomResponse(room)));
        return roomResponses;
    }

    public Optional<RoomResponse> getRoomByRoomNumber(final Integer roomNumber) {
        return roomRepository.getByRoomNumber(roomNumber).map(RoomResponse::new);
    }

    public RoomResponse createRoom(final CreateRoomRequest createRoomRequest) {
        Room room = new Room(createRoomRequest);
        List<Seat> seats = seatService.createSeats(room, createRoomRequest.getSeatsNumber());
        room.setSeats(seats);
        roomRepository.save(room);
        seatService.saveAllSeats(seats);
        return new RoomResponse(room);
    }

    @Transactional
    public boolean deleteRoom(@RequestParam Integer roomNumber) {
        return seatService.removeSeatsInRoom(roomNumber) &&
        roomRepository.getByRoomNumber(roomNumber).map(room -> {
            roomRepository.delete(room);
            return true;
        }).orElse(false);
    }

    public Optional<Seat> changeSeatStatus(final UpdateSeatRequest updateSeatRequest) {
        return seatService.changeSeatStatus(updateSeatRequest);
    }
}
