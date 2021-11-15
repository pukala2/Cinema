package com.cinema.rooms.entity;

import com.cinema.rooms.request.CreateRoomRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @OneToMany(mappedBy = "room")
    List<Seat> seats;

    public Room(CreateRoomRequest roomRequest) {
        this.seatsNumber = roomRequest.getSeatsNumber();
        this.roomNumber = roomRequest.getRoomNumber();
    }
}
