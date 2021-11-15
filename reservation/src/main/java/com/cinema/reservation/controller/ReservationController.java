package com.cinema.reservation.controller;

import com.cinema.reservation.request.DeleteReservationRequest;
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
@RequestMapping("reservation/")
public class ReservationController {

    private final ClientReservationService clientReservationService;

    public ReservationController(ClientReservationService clientReservationService) {
        this.clientReservationService = clientReservationService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<ReservationResponse>> getAll() {
        return new ResponseEntity<>(clientReservationService.getAllReservation(), HttpStatus.OK);
    }

    @GetMapping("getClients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return new ResponseEntity<>(clientReservationService.getAllClients(), HttpStatus.OK);
    }

    @PostMapping("reserve")
    public ResponseEntity<List<ReservationResponse>> reserve(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return new ResponseEntity<>(clientReservationService.reserve(createReservationRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("cancelReservation")
    public ResponseEntity<ReservationResponse> cancel(@RequestBody DeleteReservationRequest reservationRequest) {
        return new ResponseEntity<>(clientReservationService.cancelReservation(reservationRequest), HttpStatus.OK);
    }
}
