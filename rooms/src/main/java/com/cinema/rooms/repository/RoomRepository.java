package com.cinema.rooms.repository;

import com.cinema.rooms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Long  removeByRoomNumber(Integer roomNumber);
    Optional<Room> getByRoomNumber(Integer roomNumber);
}
