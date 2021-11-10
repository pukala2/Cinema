package com.cinema.reservation.client;

import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.client.model.UpdateSeatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "rooms", path = "rooms/")
public interface RoomsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "getSeatsFromRoom", consumes = "application/json")
    List<SeatResponse> getSeatsFromRoom(@RequestParam int roomNumber);

    @RequestMapping(method = RequestMethod.PUT, value = "changeSeatReservation", consumes = "application/json")
    String changeSeatReservation(@RequestBody UpdateSeatRequest updateSeatRequest);
}
