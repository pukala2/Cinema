package com.cinema.reservation.service;

import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public Optional<List<ReservationResponse>> reserve(CreateReservationRequest createReservationRequest) {
        Optional<List<SeatResponse>> seatsForReservation = seatsService.getSeatsForReservation(createReservationRequest);
        if (seatsForReservation.isEmpty()) {
            return Optional.empty();
        }
        List<ReservationResponse> reservationResponses = reservationService.save(createReservationRequest, seatsForReservation.get());

        seatsService.updateSeatsForReservation(seatsForReservation.get());
        return Optional.of(reservationResponses);
    }

    public Optional<ReservationResponse> cancelReservation(String reservationCode) {
        return reservationService.delete(reservationCode).map(seatsService::changeSeatStatus);
    }
}
