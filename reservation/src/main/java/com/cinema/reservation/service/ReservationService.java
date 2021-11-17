package com.cinema.reservation.service;

import com.cinema.reservation.client.MoviesFeignClient;
import com.cinema.reservation.client.model.FindMovieRequest;
import com.cinema.reservation.client.model.MovieResponse;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.entity.Client;
import com.cinema.reservation.entity.Reservation;
import com.cinema.reservation.exception.NotExistingMovieException;
import com.cinema.reservation.exception.NotExistingReservationCodeException;
import com.cinema.reservation.repository.ClientRepository;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.utils.ReservationCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationCodeGenerator reservationCodeGenerator;
    private final ClientRepository clientRepository;
    private final MoviesFeignClient moviesFeignClient;

    ReservationService(ReservationRepository reservationRepository, ReservationCodeGenerator reservationCodeGenerator, ClientRepository clientRepository, MoviesFeignClient moviesFeignClient) {
        this.reservationRepository = reservationRepository;
        this.reservationCodeGenerator = reservationCodeGenerator;
        this.clientRepository = clientRepository;
        this.moviesFeignClient = moviesFeignClient;
    }

    List<ReservationResponse> getReservations() {
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationRepository.findAll().forEach(reservation -> reservationResponse.add(new ReservationResponse(reservation)));
        return reservationResponse;
    }

    List<ClientResponse> getClients() {
        List<ClientResponse> clients = new ArrayList<>();
        clientRepository.findAll().forEach(client -> clients.add(new ClientResponse(client)));
        return clients;
    }

    ReservationResponse remove(DeleteReservationRequest deleteReservationRequest) {
        Reservation reservation = reservationRepository.findByReservationCode(
                deleteReservationRequest.getReservationCode());

        if (reservation == null) {
            throw new NotExistingReservationCodeException();
        }
        reservationRepository.delete(reservation);
        return new ReservationResponse(reservation);
    }

    List<ReservationResponse> save(CreateReservationRequest createReservationRequest, List<SeatResponse> seatsForReservation) {
        List<ReservationResponse> reservationResponses = new ArrayList<>();

        checkTitle(new FindMovieRequest(createReservationRequest.getMovieTitle()));

        List<Reservation> reservations = saveReservation(createReservationRequest, seatsForReservation);
        reservations.forEach(reservation -> {
            reservationResponses.add(new ReservationResponse(reservation));
        });
        return reservationResponses;
    }

    private void checkTitle(FindMovieRequest findMovieRequest) {
        MovieResponse movie = moviesFeignClient.getByTitle(findMovieRequest);

        if (movie == null) {
            throw new NotExistingMovieException();
        }
    }

    private List<Reservation> saveReservation(CreateReservationRequest createReservationRequest, List<SeatResponse> seatsForReservation) {
        return seatsForReservation.stream().map(seatResponse -> {
            Reservation reservation = createReservation(createReservationRequest, seatResponse);
            Client client = clientRepository.save(reservation.getClient());
            reservation = reservationRepository.save(reservation);
            reservation.setClient(client);
            return reservation;
        }).collect(Collectors.toList());
    }

    private Reservation createReservation(CreateReservationRequest createReservationRequest, SeatResponse seatResponse) {
        return Reservation.builder()
                .roomNumber(seatResponse.getRoomNumber())
                .seatNumber(seatResponse.getSeatNumber())
                .movieTitle(createReservationRequest.getMovieTitle())
                .client(createReservationRequest.getClient())
                .reservationCode(reservationCodeGenerator.generateCode())
                .build();
    }
}
