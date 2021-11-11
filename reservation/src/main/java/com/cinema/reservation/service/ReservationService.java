package com.cinema.reservation.service;

import com.cinema.reservation.entity.Client;
import com.cinema.reservation.entity.Reservation;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.repository.ClientRepository;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.SeatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    ClientRepository clientRepository;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public List<SeatResponse> getSeatsForReservationFromRoom(List<SeatResponse> seatsFromRoom,
                                                             CreateReservationRequest createReservationRequest) {

        if (seatsFromRoom == null || seatsFromRoom.isEmpty()) {
            return null;
        }
        List<SeatResponse> seatsForReservation = getSeatsWithTheSameNumber(seatsFromRoom,
                createReservationRequest.getSeatRequests());

        if (seatsForReservation.stream().anyMatch(SeatResponse::isBocked)) {
            return null;
        }
        seatsForReservation.forEach(seatResponseRes -> seatResponseRes.setBocked(true));
        return seatsForReservation;
    }

    private List<SeatResponse> getSeatsWithTheSameNumber(List<SeatResponse> seatsFromRoom, List<SeatRequest> seatsRequest) {
        List<SeatResponse> seatsForReservation = new ArrayList<>();
        seatsFromRoom.forEach(seatResponseRes -> seatsRequest.forEach(seatReq -> {
            if (seatResponseRes.getSeatNumber() == seatReq.getSeatsNumber()) {
                seatsForReservation.add(seatResponseRes);
            }}));
        return seatsForReservation;
    }

    @Transactional
    public List<Reservation> saveReservation(CreateReservationRequest createReservationRequest, List<SeatResponse> seatsForReservation) {
        if(createReservationRequest.getSeatRequests() == null || createReservationRequest.getSeatRequests().isEmpty()) {
            return null;
        }
        List<Reservation> reservations = new ArrayList<>();

        seatsForReservation.forEach(seatResponse -> {
            Reservation reservation = new Reservation();
            reservation.setRoomNumber(seatResponse.getRoomNumber());
            reservation.setSeatsNumber(seatResponse.getSeatNumber());
            reservation.setMovieTitle(createReservationRequest.getMovieTitle());
            setClient(createReservationRequest.getClient(), reservation);

            reservation = reservationRepository.save(reservation);
            reservation.getClient().setReservation(reservation);
            clientRepository.save(reservation.getClient());

            reservations.add(reservation);
        });
        return reservations;
    }
    private void setClient(Client clientReq, Reservation reservation) {
        Client client = new Client();
        client.setReservation(clientReq.getReservation());
        client.setEmail(clientReq.getEmail());
        client.setName(clientReq.getName());
        client.setSurname(clientReq.getSurname());
        client.setPaid(clientReq.isPaid());

        reservation.setClient(client);
    }
}
