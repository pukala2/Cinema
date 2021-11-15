package com.cinema.reservation.service;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.Room;
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
        Room room = roomsFeignClient.getRoomByRoomNumber(roomNumber);

        return SeatsHelper.getSeatsForReservationFromRoom(room.getSeats(),
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

        UpdateSeatRequest updateSeatRequest = UpdateSeatRequest.builder()
                .seatNumber(reservationResponse.getSeatNumber())
                .roomNumber(reservationResponse.getRoomNumber())
                .isBocked(false)
                .build();
        roomsFeignClient.changeSeatReservation(updateSeatRequest);
        return reservationResponse;
    }
}
