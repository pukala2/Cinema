package com.cinema.rooms.service;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.repository.RoomRepository;
import com.cinema.rooms.repository.SeatRepository;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    RoomService sut;

    @Mock
    SeatRepository seatRepository;

    @Test
    void shouldCreateRoom() {

        CreateRoomRequest createRoomRequest = new CreateRoomRequest();
        createRoomRequest.setRoomNumber(1);
        createRoomRequest.setSeatsNumber(5);

        Room result = sut.createRoom(createRoomRequest);

        Assertions.assertEquals(1, result.getRoomNumber());
        Assertions.assertEquals(5, result.getSeatsNumber());
        Assertions.assertEquals(5, result.getSeats().size());
    }

    @Test
    void shouldChangeSeatReservation() {

        UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
        updateSeatRequest.setRoomNumber(1);
        updateSeatRequest.setBocked(true);
        updateSeatRequest.setSeatNumber(1);

        Seat seat = new Seat();
        seat.setBocked(false);
        seat.setSeatNumber(1);
        seat.setRoomNumber(1);

        Mockito.when(seatRepository.getByRoomNumber(updateSeatRequest.getRoomNumber())).
                thenReturn(List.of(seat));
        Optional<Seat> result = sut.changeSeatReservation(updateSeatRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.get().getRoomNumber());
        Assertions.assertTrue(result.get().isBocked());
        Assertions.assertEquals(1, result.get().getSeatNumber());
    }

    @Test
    void shouldNotChangeSeatReservationSeatNumberNotMatch() {

        UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
        updateSeatRequest.setRoomNumber(1);
        updateSeatRequest.setBocked(true);
        updateSeatRequest.setSeatNumber(1);

        Seat seat = new Seat();
        seat.setBocked(false);
        seat.setSeatNumber(2);
        seat.setRoomNumber(1);

        Mockito.when(seatRepository.getByRoomNumber(updateSeatRequest.getRoomNumber())).
                thenReturn(List.of(seat));
        Optional<Seat> result = sut.changeSeatReservation(updateSeatRequest);

        Assertions.assertTrue(result.isEmpty());
    }


}