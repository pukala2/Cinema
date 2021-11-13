package com.cinema.reservation.service;

import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.request.CreateSeatRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ClientReservationService sut;

    private SeatResponse createSeatResponse(Integer roomNumber, Integer seatNumber, Boolean isBocked) {
        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setIsBocked(isBocked);
        seatResponse.setSeatNumber(seatNumber);
        seatResponse.setRoomNumber(roomNumber);
        return seatResponse;
    }

    private CreateSeatRequest createSeatRequest(Integer roomNumber, Integer seatsNumber) {
        CreateSeatRequest createSeatRequest = new CreateSeatRequest();
        createSeatRequest.setRoomNumber(roomNumber);
        createSeatRequest.setSeatNumber(seatsNumber);
        return createSeatRequest;
    }
    /*
        reserve
        cancelReservation
     */

//    @Test
//    void shouldReturnNullWhenEmptySeatsFromRoomy() {
//        final List<SeatResponse> seatsFromRoom = new ArrayList<>();
//        final CreateReservationRequest createReservationRequest = new CreateReservationRequest();
//
//        final var result =
//                sut.getSeatsForReservationFromRoom(seatsFromRoom, createReservationRequest);
//
//        Assertions.assertNull(result);
//    }
//
//    @Test
//    void shouldReturnNullWhenSeatsAreBocked() {
//
//        final List<SeatResponse> seatsFromRoom = Arrays.asList(
//                createSeatResponse(1, 1, true),
//                createSeatResponse(1, 2, true),
//                createSeatResponse(1, 3, true)
//        );
//        CreateReservationRequest createReservationRequest = new CreateReservationRequest();
//        createReservationRequest.setCreateSeatRequests(Arrays.asList(
//                createSeatRequest(1, 2),
//                createSeatRequest(1, 3)
//        ));
//
//        final var result =
//                sut.getSeatsForReservationFromRoom(seatsFromRoom, createReservationRequest);
//
//        Assertions.assertNull(result);
//    }
//
//    @Test
//    void shouldReturnSeatsForReservation() {
//
//        final List<SeatResponse> seatsFromRoom = Arrays.asList(
//                createSeatResponse(1, 1, false),
//                createSeatResponse(1, 2, false),
//                createSeatResponse(1, 3, false)
//        );
//        CreateReservationRequest createReservationRequest = new CreateReservationRequest();
//        createReservationRequest.setCreateSeatRequests(Arrays.asList(
//                createSeatRequest(1, 2),
//                createSeatRequest(1, 3)
//        ));
//
//        final var result =
//                sut.getSeatsForReservationFromRoom(seatsFromRoom, createReservationRequest);
//
//        final List<SeatResponse> expectedSeatsForReservation = Arrays.asList(
//                createSeatResponse(1, 2, true),
//                createSeatResponse(1, 3, true)
//        );
//        Assertions.assertEquals(expectedSeatsForReservation, result);
//    }
}