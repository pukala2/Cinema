package com.cinema.reservation.controller;

import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.service.ClientReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ClientReservationService clientReservationService;

    public ReservationController(ClientReservationService clientReservationService) {
        this.clientReservationService = clientReservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponse>> getAll() {
        return new ResponseEntity<>(clientReservationService.getAllReservation(), HttpStatus.OK);
    }

    @GetMapping("getClients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return new ResponseEntity<>(clientReservationService.getAllClients(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<List<ReservationResponse>> reserve(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return clientReservationService.reserve(createReservationRequest).map(reservation
                -> new ResponseEntity<List<ReservationResponse>>(reservation, HttpStatus.CREATED)).orElseGet(
                        () -> new ResponseEntity<List<ReservationResponse>>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/cancelReservation/{reservationCode}")
    public ResponseEntity<ReservationResponse> cancel(@PathVariable String reservationCode) {
        return clientReservationService.cancelReservation(reservationCode)
                .map(reservationResponse -> new ResponseEntity<>(reservationResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
