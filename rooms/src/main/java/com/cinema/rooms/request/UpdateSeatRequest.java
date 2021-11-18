package com.cinema.rooms.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSeatRequest {
    private Integer seatNumber;
    private Boolean isBocked;
    private Integer roomNumber;
}
