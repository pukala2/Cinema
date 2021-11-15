package com.cinema.rooms.service;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.exception.NotExistingSeatException;
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

    void removeSeatsInRoom(Integer roomNumber) {
        List<Seat> seats = getSeatsFromRoom(roomNumber);
        seatRepository.deleteAll(seats);
    }

    List<Seat> getSeatsFromRoom(final Integer roomNumber) {
        return seatRepository.getByRoomNumber(roomNumber);
    }

    List<Seat> saveAllSeats(List<Seat> seats) {
        return seatRepository.saveAll(seats);
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

    Seat changeSeatStatus(final UpdateSeatRequest updateSeatRequest) {
        Optional<Seat> seat = getSeatFromRoom(updateSeatRequest.getRoomNumber(),
                updateSeatRequest.getSeatNumber());

        if (seat.isPresent()) {
            seat.get().setIsBocked(updateSeatRequest.getIsBocked());
            return seatRepository.save(seat.get());
        }
        throw new NotExistingSeatException();
    }

    private Optional<Seat> getSeatFromRoom(Integer roomNumber, Integer seatNumber) {
        List<Seat> seats = getSeatsFromRoom(roomNumber);
        return seats.stream().filter(s -> Objects.equals(s.getSeatNumber(), seatNumber))
                .findAny();
    }

}
