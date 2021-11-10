package com.cinema.reservation.controller;

import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("reservation/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("getAll")
    public List<ReservationResponse> getAll() {
        List<ReservationResponse> reservationResponse = new ArrayList<>();
        reservationService.getAll().forEach(reservation -> reservationResponse.add(new ReservationResponse(reservation)));
        return reservationResponse;
    }

}
