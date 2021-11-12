package com.cinema.reservation.service;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import com.cinema.reservation.entity.Reservation;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.exception.ApiRequestException;
import com.cinema.reservation.repository.ClientRepository;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.CreateSeatRequest;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CodeGenerator reservationCodeGenerator;

    @Autowired
    private RoomsFeignClient roomsFeignClient;

    public List<ReservationResponse> getAll() {
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationRepository.findAll().forEach(reservation -> reservationResponse.add(new ReservationResponse(reservation)));
        return reservationResponse;
    }

    @Transactional
    public List<ReservationResponse> reserve(CreateReservationRequest createReservationRequest) {

        final int roomNumber = createReservationRequest.getCreateSeatRequests().get(0).getRoomNumber();
        List<SeatResponse> seatsFromRoom = roomsFeignClient.getSeatsFromRoom(roomNumber);
        List<SeatResponse> seatsForReservation = getSeatsForReservationFromRoom(seatsFromRoom,
                createReservationRequest);
        List<ReservationResponse> reservationResponses = saveReservations(createReservationRequest, seatsForReservation);

        updateRoomsReservation(seatsForReservation);
        return reservationResponses;
    }

    private List<SeatResponse> getSeatsForReservationFromRoom(List<SeatResponse> seatsFromRoom,
                                                              CreateReservationRequest createReservationRequest) {

        if (seatsFromRoom == null || seatsFromRoom.isEmpty()) {
            throw new ApiRequestException("Seats for this room number are empty", HttpStatus.BAD_REQUEST);
        }
        List<SeatResponse> seatsForReservation = getSeatsWithTheSameNumber(seatsFromRoom,
                createReservationRequest.getCreateSeatRequests());

        if (seatsForReservation.stream().anyMatch(SeatResponse::getIsBocked)) {
            throw new ApiRequestException("Seat can not be booked", HttpStatus.BAD_REQUEST);
        }
        seatsForReservation.forEach(seatResponseRes -> seatResponseRes.setIsBocked(true));
        return seatsForReservation;
    }

    private List<SeatResponse> getSeatsWithTheSameNumber(List<SeatResponse> seatsFromRoom, List<CreateSeatRequest> seatsRequest) {
        List<SeatResponse> seatsForReservation = new ArrayList<>();
        seatsFromRoom.forEach(seatResponseRes -> seatsRequest.forEach(seatReq -> {
            if (Objects.equals(seatResponseRes.getSeatNumber(), seatReq.getSeatNumber())) {
                seatsForReservation.add(seatResponseRes);
            }}));
        return seatsForReservation;
    }


    private void updateRoomsReservation(List<SeatResponse> seatsForReservation) {
        seatsForReservation.forEach(seat -> {
            UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
            updateSeatRequest.setSeatNumber(seat.getSeatNumber());
            updateSeatRequest.setRoomNumber(seat.getRoomNumber());
            updateSeatRequest.setIsBocked(true);
            roomsFeignClient.changeSeatReservation(updateSeatRequest);
        });
    }

    private List<ReservationResponse> saveReservations(CreateReservationRequest createReservationRequest, List<SeatResponse> seatsForReservation) {
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        saveReservation(createReservationRequest, seatsForReservation).forEach(reservation ->
                reservationResponses.add(new ReservationResponse(reservation)));

        return reservationResponses;
    }

    private List<Reservation> saveReservation(CreateReservationRequest createReservationRequest, List<SeatResponse> seatsForReservation) {
        return seatsForReservation.stream().map(seatResponse -> {
            Reservation reservation = createReservation(createReservationRequest, seatResponse);
            reservation = reservationRepository.save(reservation);
            reservation.getClient().setReservation(reservation);
            clientRepository.save(reservation.getClient());
            return reservation;
        }).collect(Collectors.toList());
    }

    private Reservation createReservation(CreateReservationRequest createReservationRequest, SeatResponse seatResponse) {
        Reservation reservation = new Reservation();
        reservation.setRoomNumber(seatResponse.getRoomNumber());
        reservation.setSeatNumber(seatResponse.getSeatNumber());
        reservation.setMovieTitle(createReservationRequest.getMovieTitle());
        reservation.setReservationCode(reservationCodeGenerator.generateCode());
        reservation.setClient(createReservationRequest.getClient());
        return reservation;
    }

    public ReservationResponse cancelReservation(DeleteReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = removeReservation(reservationRequest);

        UpdateSeatRequest updateSeatRequest = new UpdateSeatRequest();
        updateSeatRequest.setIsBocked(false);
        roomsFeignClient.changeSeatReservation(updateSeatRequest);
        return reservationResponse;
    }

    private ReservationResponse removeReservation(DeleteReservationRequest deleteReservationRequest) {

        Reservation reservation = reservationRepository.findByReservationCode(
                deleteReservationRequest.getReservationCode());

        if (reservation == null) {
            throw new ApiRequestException("Reservation with this code does not exist", HttpStatus.NOT_FOUND);
        }
        reservationRepository.delete(reservation);
        return new ReservationResponse(reservation);
    }
}
