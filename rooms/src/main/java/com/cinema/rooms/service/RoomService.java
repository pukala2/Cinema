package com.cinema.rooms.service;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.repository.RoomRepository;
import com.cinema.rooms.repository.SeatRepository;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public List<Seat> getSeatsFromRoom(final int roomNumber) {
        return seatRepository.getByRoomNumber(roomNumber);
    }

    public Room createRoom(final CreateRoomRequest createRoomRequest) {

        Room room = new Room(createRoomRequest);
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < createRoomRequest.getSeatsNumber(); i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i + 1);
            seat.setBocked(false);
            seat.setRoom(room);
            seats.add(seat);
            seat.setRoomNumber(room.getRoomNumber());
        }
        room.setSeats(seats);

        return room;
    }

    public Room saveRoom(final Room room) {
        return  roomRepository.save(room);
    }

    public List<Seat> saveSeats(final List<Seat> seats) {
        return seatRepository.saveAll(seats);
    }

    public Seat saveSeat(final Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByRoomNumber(int roomNumber) {
        return seatRepository.getByRoomNumber(roomNumber);
    }

    public void removeSeats(List<Seat> seats) {
        seatRepository.deleteAll(seats);
    }

    public void removeRoomByRoomNumber(int roomNumber) {
        roomRepository.removeByRoomNumber(roomNumber);
    }

    public Optional<Seat> changeSeatReservation(final UpdateSeatRequest updateSeatRequest) {

        Optional<Seat> seat = getSeatsFromRoom(updateSeatRequest.getRoomNumber(),
                updateSeatRequest.getSeatNumber());

        if (seat.isPresent()) {
            seat.get().setBocked(updateSeatRequest.isBocked());
            return seat;
        }
        return seat;
    }

    private Optional<Seat> getSeatsFromRoom(int roomNumber, int seatNumber) {
        List<Seat> seats = seatRepository.getByRoomNumber(roomNumber);
        return seats.stream().filter(s -> s.getSeatNumber() == seatNumber)
                .findAny();
    }
}
