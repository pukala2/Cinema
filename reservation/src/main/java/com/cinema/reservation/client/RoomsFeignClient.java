package com.cinema.reservation.client;

import com.cinema.reservation.client.model.RoomResponse;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "rooms", path = "room/")
public interface RoomsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/getRoomByRoomNumber/{roomNumber}", consumes = "application/json")
    ResponseEntity<RoomResponse> getRoomByRoomNumber(@PathVariable Integer roomNumber);

    @RequestMapping(method = RequestMethod.PUT, value = "changeSeatReservation", consumes = "application/json")
    ResponseEntity<SeatResponse> changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest);
}
