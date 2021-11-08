package com.cinema.rooms.repository;

import com.cinema.rooms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Long  removeByRoomNumber(int roomNumber);
    Room getByRoomNumber(int roomNumber);
}
