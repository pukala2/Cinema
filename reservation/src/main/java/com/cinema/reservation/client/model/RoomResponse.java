package com.cinema.reservation.client.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {
    private Long id;
    private Integer roomNumber;
    private Integer seatsNumber;
    private List<SeatResponse> seats;
}
