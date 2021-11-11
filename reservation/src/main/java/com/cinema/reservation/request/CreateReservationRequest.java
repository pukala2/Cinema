package com.cinema.reservation.request;

import com.cinema.reservation.entity.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateReservationRequest {
    private List<SeatRequest> seatRequests;
    private String movieTitle;
    private Client client;
}
