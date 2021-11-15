package com.cinema.reservation.service;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.Room;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.entity.Client;
import com.cinema.reservation.exception.BookedSeatException;
import com.cinema.reservation.exception.SeatsNotMatchException;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.CreateSeatRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SeatsServiceTest {

    @InjectMocks
    private SeatsService sut;

    @Mock
    RoomsFeignClient roomsFeignClient;

    private static final int ROOM_NUMBER = 1;
    private static final String MOVIE_TITLE = "Matrix";

    private SeatResponse createSeatResponse(Integer roomNumber, Integer seatNumber, Boolean isBocked) {
        return SeatResponse.builder().isBocked(isBocked).seatNumber(seatNumber).roomNumber(roomNumber).build();
    }

    private Client createClient() {
        final Client client = new Client();
        client.setName("John");
        client.setSurname("Smith");
        client.setEmail("john.smith@gmail.com");
        client.setPaid(true);
        return client;
    }

    @Test
    void shouldGiveExceptionWhenReserveAndSeatsAreNotMatchingFromRequest() {
        final List<SeatResponse> seatsFromRoom = Arrays.asList(
                createSeatResponse(ROOM_NUMBER, 1, false),
                createSeatResponse(ROOM_NUMBER, 2, false),
                createSeatResponse(ROOM_NUMBER, 3, false)
        );

        final Client client = createClient();
        final CreateReservationRequest createReservationRequest = CreateReservationRequest.builder()
                .createSeatRequests(Arrays.asList(
                        CreateSeatRequest.builder().roomNumber(ROOM_NUMBER).seatNumber(4).build(),
                        CreateSeatRequest.builder().roomNumber(ROOM_NUMBER).seatNumber(5).build()))
                .movieTitle(MOVIE_TITLE)
                .client(client)
                .build();

        Room room = Room.builder().seats(seatsFromRoom).build();
        Mockito.when(roomsFeignClient.getRoomByRoomNumber(ROOM_NUMBER)).thenReturn(room);

        SeatsNotMatchException thrown = Assertions.assertThrows(SeatsNotMatchException.class, () -> {
            sut.getSeatsForReservation(createReservationRequest);
        });

        Assertions.assertEquals("Seats from request does not match to seats from room",
                thrown.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
    }

    @Test
    void shouldGiveExceptionWhenReserveAndSeatFromRequestIsBookedInRoom() {
        final List<SeatResponse> seatsFromRoom = Arrays.asList(
                createSeatResponse(ROOM_NUMBER, 1, false),
                createSeatResponse(ROOM_NUMBER, 2, true),
                createSeatResponse(ROOM_NUMBER, 3, false)
        );

        final Client client = createClient();
        final CreateReservationRequest createReservationRequest = CreateReservationRequest.builder()
                .createSeatRequests(Arrays.asList(
                        CreateSeatRequest.builder().roomNumber(ROOM_NUMBER).seatNumber(2).build(),
                        CreateSeatRequest.builder().roomNumber(ROOM_NUMBER).seatNumber(3).build()))
                .movieTitle(MOVIE_TITLE)
                .client(client)
                .build();

        Room room = Room.builder().seats(seatsFromRoom).build();
        Mockito.when(roomsFeignClient.getRoomByRoomNumber(ROOM_NUMBER)).thenReturn(room);

        BookedSeatException thrown = Assertions.assertThrows(BookedSeatException.class, () -> {
            sut.getSeatsForReservation(createReservationRequest);
        });

        Assertions.assertEquals("Seat from request is already booked",
                thrown.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpStatus());
    }

    @Test
    void shouldGiveSeatsForReservation() {
        final List<SeatResponse> seatsFromRoom = Arrays.asList(
                createSeatResponse(ROOM_NUMBER, 1, false),
                createSeatResponse(ROOM_NUMBER, 2, true),
                createSeatResponse(ROOM_NUMBER, 3, false)
        );

        final Client client = createClient();
        final CreateReservationRequest createReservationRequest = CreateReservationRequest.builder()
                .createSeatRequests(Arrays.asList(
                        CreateSeatRequest.builder().roomNumber(ROOM_NUMBER).seatNumber(1).build(),
                        CreateSeatRequest.builder().roomNumber(ROOM_NUMBER).seatNumber(3).build()))
                .movieTitle(MOVIE_TITLE)
                .client(client)
                .build();

        Room room = Room.builder().seats(seatsFromRoom).build();
        Mockito.when(roomsFeignClient.getRoomByRoomNumber(ROOM_NUMBER)).thenReturn(room);

        List<SeatResponse> result = sut.getSeatsForReservation(createReservationRequest);
        List<SeatResponse> expected = Arrays.asList(
                createSeatResponse(ROOM_NUMBER, 1, true),
                createSeatResponse(ROOM_NUMBER, 3, true));

        Assertions.assertEquals(expected, result);
    }

}