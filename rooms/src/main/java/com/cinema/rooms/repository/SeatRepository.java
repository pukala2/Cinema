package com.cinema.rooms.repository;

import com.cinema.rooms.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> getByRoomNumber(int roomNumber);
}
