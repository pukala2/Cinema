package com.cinema.reservation.controller;

import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.service.ClientReservationService;
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
    public List<ReservationResponse> getAll() {
        return clientReservationService.getAllReservation();
    }

    @GetMapping("getClients")
    public List<ClientResponse> getAllClients() {
        return clientReservationService.getAllClients();
    }

    @PostMapping("reserve")
    public List<ReservationResponse> reserve(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return clientReservationService.reserve(createReservationRequest);
    }

    @DeleteMapping("cancelReservation")
    public ReservationResponse cancel(@RequestBody DeleteReservationRequest reservationRequest) {
        return clientReservationService.cancelReservation(reservationRequest);
    }
}
