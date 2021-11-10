package com.cinema.reservation.controller;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("reservation/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomsFeignClient roomsFeignClient;

    @GetMapping("getAll")
    public List<ReservationResponse> getAll() {
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationService.getAll().forEach(reservation -> reservationResponse.add(new ReservationResponse(reservation)));
        return reservationResponse;
    }

    @PostMapping("reserve")
    public List<ReservationResponse> reserveSeats(@RequestBody CreateReservationRequest createReservationRequest) {

        if (createReservationRequest.getSeatRequests() == null || createReservationRequest.getSeatRequests().isEmpty()) {
            return null;
        }

        final int roomNumber = createReservationRequest.getSeatRequests().get(0).getRoomNumber();
        List<SeatResponse> seatsFromRoom = roomsFeignClient.getSeatsFromRoom(roomNumber);
        List<SeatResponse> seatsForReservation = reservationService.getSeatsForReservationFromRoom(seatsFromRoom,
                createReservationRequest);
        List<ReservationResponse> reservationResponses = saveReservations(createReservationRequest, seatsForReservation);

        updateRoomsReservation(seatsForReservation);
        return reservationResponses;
    }

    private List<ReservationResponse> saveReservations(CreateReservationRequest createReservationRequest, List<SeatResponse> seatsForReservation) {
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        reservationService.saveReservation(createReservationRequest, seatsForReservation).forEach(reservation ->
                reservationResponses.add(new ReservationResponse(reservation)));

        return reservationResponses;
    }

    private void updateRoomsReservation(List<SeatResponse> seatsForReservation) {
        seatsForReservation.forEach(seat -> {
            UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
            updateSeatRequest.setSeatNumber(seat.getSeatNumber());
            updateSeatRequest.setRoomNumber(seat.getRoomNumber());
            updateSeatRequest.setBocked(true);
            roomsFeignClient.changeSeatReservation(updateSeatRequest);
        });
    }
}
