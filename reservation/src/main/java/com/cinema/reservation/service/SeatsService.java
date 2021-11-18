package com.cinema.reservation.service;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import com.cinema.reservation.entity.Reservation;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.utils.SeatsHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SeatsService {
    private final RoomsFeignClient roomsFeignClient;

    SeatsService(RoomsFeignClient roomsFeignClient) {
        this.roomsFeignClient = roomsFeignClient;
    }

    Optional<List<SeatResponse>> getSeatsForReservation(CreateReservationRequest createReservationRequest) {
        final int roomNumber = createReservationRequest.getCreateSeatRequests().get(0).getRoomNumber();

        var room = roomsFeignClient.getRoomByRoomNumber(roomNumber);

        return SeatsHelper.getSeatsForReservationFromRoom(Objects.requireNonNull(room.getBody()).getSeats(),
                createReservationRequest);
    }

    void updateSeatsForReservation(List<SeatResponse> seatsForReservation) {
        seatsForReservation.forEach(seat -> {
            roomsFeignClient.changeSeatReservation(UpdateSeatRequest.builder()
                    .seatNumber(seat.getSeatNumber())
                    .roomNumber(seat.getRoomNumber())
                    .isBocked(true)
                    .build());
        });
    }

    ReservationResponse changeSeatStatus(Reservation reservation) {

        UpdateSeatRequest updateSeatRequest = UpdateSeatRequest.builder()
                .seatNumber(reservation.getSeatNumber())
                .roomNumber(reservation.getRoomNumber())
                .isBocked(false)
                .build();
        roomsFeignClient.changeSeatReservation(updateSeatRequest);
        return new ReservationResponse(reservation);
    }
}
