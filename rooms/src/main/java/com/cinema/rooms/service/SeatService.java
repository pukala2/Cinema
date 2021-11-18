package com.cinema.rooms.service;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.repository.SeatRepository;
import com.cinema.rooms.request.UpdateSeatRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
class SeatService {

    final private SeatRepository seatRepository;

    SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    boolean removeSeatsInRoom(Integer roomNumber) {
        return getSeatsFromRoom(roomNumber).map(seats -> {
            seatRepository.deleteAll(seats);
            return true;
        }).orElse(false);
    }

    Optional<List<Seat>> getSeatsFromRoom(final Integer roomNumber) {
        return seatRepository.getByRoomNumber(roomNumber);
    }

    void saveAllSeats(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    List<Seat> createSeats(Room room, int seatsNumber) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < seatsNumber; i++) {
            Seat seat = Seat.builder().seatNumber(i + 1).isBocked(false).room(room).build();
            seats.add(seat);
            seat.setRoomNumber(room.getRoomNumber());
        }
        return seats;
    }

    Optional<Seat> changeSeatStatus(final UpdateSeatRequest updateSeatRequest) {
        return getSeatFromRoom(updateSeatRequest.getRoomNumber(),
                updateSeatRequest.getSeatNumber())
                .map(s -> changeStatus(s, updateSeatRequest.getIsBocked()));
    }

    private Optional<Seat> getSeatFromRoom(Integer roomNumber, Integer seatNumber) {
        return getSeatsFromRoom(roomNumber).map(ArrayList::new)
                .orElse(new ArrayList<>())
                .stream().filter(s -> Objects.equals(s.getSeatNumber(), seatNumber)).findAny();
    }

    private Seat changeStatus(Seat seat, boolean status) {
        seat.setIsBocked(status);
        return seatRepository.save(seat);
    }
}
