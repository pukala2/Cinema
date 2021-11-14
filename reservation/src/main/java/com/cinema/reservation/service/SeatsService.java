package com.cinema.reservation.service;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.utils.SeatsHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {
    private final RoomsFeignClient roomsFeignClient;

    SeatsService(RoomsFeignClient roomsFeignClient) {
        this.roomsFeignClient = roomsFeignClient;
    }

    List<SeatResponse> getSeatsForReservation(CreateReservationRequest createReservationRequest) {

        final int roomNumber = createReservationRequest.getCreateSeatRequests().get(0).getRoomNumber();
        List<SeatResponse> seatsFromRoom = roomsFeignClient.getSeatsFromRoom(roomNumber);

        return SeatsHelper.getSeatsForReservationFromRoom(seatsFromRoom,
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

    ReservationResponse changeSeatStatus(ReservationResponse reservationResponse) {
        return new ReservationResponse();
    }
}
