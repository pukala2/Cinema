package com.cinema.reservation.service;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.repository.ClientRepository;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.utils.ReservationCodeGenerator;
import com.cinema.reservation.utils.SeatsFinder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientReservationService {

    private final RoomsFeignClient roomsFeignClient;
    private final ReservationService reservationService;

    public ClientReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository,
                                    ReservationCodeGenerator reservationCodeGenerator, RoomsFeignClient roomsFeignClient, ReservationService reservationService) {
        this.roomsFeignClient = roomsFeignClient;
        this.reservationService = reservationService;
    }

    public List<ReservationResponse> getAllReservation() {
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationService.getAll().forEach(reservation -> reservationResponse.add(new ReservationResponse(reservation)));
        return reservationResponse;
    }

    @Transactional
    public List<ReservationResponse> reserve(CreateReservationRequest createReservationRequest) {

        final int roomNumber = createReservationRequest.getCreateSeatRequests().get(0).getRoomNumber();
        List<SeatResponse> seatsFromRoom = roomsFeignClient.getSeatsFromRoom(roomNumber);
        List<SeatResponse> seatsForReservation = SeatsFinder.getSeatsForReservationFromRoom(seatsFromRoom,
                createReservationRequest);
        List<ReservationResponse> reservationResponses = reservationService.save(createReservationRequest, seatsForReservation);

        updateSeatsForReservation(seatsForReservation);
        return reservationResponses;
    }

    private void updateSeatsForReservation(List<SeatResponse> seatsForReservation) {
        seatsForReservation.forEach(seat -> {
            UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
            updateSeatRequest.setSeatNumber(seat.getSeatNumber());
            updateSeatRequest.setRoomNumber(seat.getRoomNumber());
            updateSeatRequest.setIsBocked(true);
            roomsFeignClient.changeSeatReservation(updateSeatRequest);
        });
    }

    public ReservationResponse cancelReservation(DeleteReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.remove(reservationRequest);

        UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
        updateSeatRequest.setIsBocked(false);
        roomsFeignClient.changeSeatReservation(updateSeatRequest);
        return reservationResponse;
    }
}
