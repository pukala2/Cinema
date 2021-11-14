package com.cinema.reservation.service;

import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClientReservationService {

    private final SeatsService seatsService;
    private final ReservationService reservationService;

    public ClientReservationService(SeatsService seatsService, ReservationService reservationService) {
        this.seatsService = seatsService;
        this.reservationService = reservationService;
    }

    public List<ReservationResponse> getAllReservation() {
        return reservationService.getReservations();
    }

    public List<ClientResponse> getAllClients() {
        return reservationService.getClients();
    }

    @Transactional
    public List<ReservationResponse> reserve(CreateReservationRequest createReservationRequest) {
        List<SeatResponse> seatsForReservation = seatsService.getSeatsForReservation(createReservationRequest);
        List<ReservationResponse> reservationResponses = reservationService.save(createReservationRequest, seatsForReservation);

        seatsService.updateSeatsForReservation(seatsForReservation);
        return reservationResponses;
    }

    public ReservationResponse cancelReservation(DeleteReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.remove(reservationRequest);
        seatsService.changeSeatStatus(reservationResponse);
        return reservationResponse;
    }
}
