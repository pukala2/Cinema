package com.cinema.reservation.request;

import com.cinema.reservation.entity.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateReservationRequest {
    List<SeatRequest> seatRequests;
    Client client;
}
