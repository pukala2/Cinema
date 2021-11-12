package com.cinema.reservation.controller;

import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("reservation/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("getAll")
    public List<ReservationResponse> getAll() {
        return reservationService.getAll();
    }

    @PostMapping("reserve")
    public List<ReservationResponse> reserve(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return reservationService.reserve(createReservationRequest);
    }

    @DeleteMapping("cancelReservation")
    public ReservationResponse cancelReservation(@RequestBody DeleteReservationRequest reservationRequest) {
        return reservationService.cancelReservation(reservationRequest);
    }
}
