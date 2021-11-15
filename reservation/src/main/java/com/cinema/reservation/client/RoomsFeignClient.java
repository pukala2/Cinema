package com.cinema.reservation.client;

import com.cinema.reservation.client.model.Room;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "rooms", path = "rooms/")
public interface RoomsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "getRoomByRoomNumber", consumes = "application/json")
    Room getRoomByRoomNumber(@RequestParam Integer roomNumber);

    @RequestMapping(method = RequestMethod.PUT, value = "changeSeatReservation", consumes = "application/json")
    String changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest);
}
