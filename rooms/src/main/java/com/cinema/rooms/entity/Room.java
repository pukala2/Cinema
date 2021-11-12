package com.cinema.rooms.entity;

import com.cinema.rooms.request.CreateRoomRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
