package com.cinema.rooms.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "is_bocked")
    private Boolean isBocked;

    @Column(name = "room_number")
    private Integer roomNumber;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
