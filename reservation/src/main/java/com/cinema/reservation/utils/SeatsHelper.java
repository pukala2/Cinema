package com.cinema.reservation.utils;

import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.CreateSeatRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SeatsHelper {

    static public Optional<List<SeatResponse>> getSeatsForReservationFromRoom(List<SeatResponse> seatsFromRoom,
                                                              CreateReservationRequest createReservationRequest) {
        List<SeatResponse> seatsForReservation = getSeatsWithTheSameNumber(seatsFromRoom,
                createReservationRequest.getCreateSeatRequests());
        if (seatsForReservation.isEmpty() || seatsForReservation.size() != createReservationRequest.getCreateSeatRequests().size()) {
            return Optional.empty();
        }
        if (seatsForReservation.stream().anyMatch(SeatResponse::getIsBocked)) {
            return Optional.empty();
        }
        seatsForReservation.forEach(seatResponseRes -> seatResponseRes.setIsBocked(true));
        return Optional.of(seatsForReservation);
    }

    static private List<SeatResponse> getSeatsWithTheSameNumber(List<SeatResponse> seatsFromRoom, List<CreateSeatRequest> seatsRequest) {
        List<SeatResponse> seatsForReservation = new ArrayList<>();
        seatsFromRoom.forEach(seatResponseRes -> seatsRequest.forEach(seatReq -> {
            if (Objects.equals(seatResponseRes.getSeatNumber(), seatReq.getSeatNumber())) {
                seatsForReservation.add(seatResponseRes);
            }}));
        return seatsForReservation;
    }
}
