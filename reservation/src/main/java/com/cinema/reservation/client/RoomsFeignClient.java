package com.cinema.reservation.client;

import com.cinema.reservation.model.Room;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("rooms")
public interface RoomsFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "getAll", consumes = "application/json")
    List<Room> getAll();
}
