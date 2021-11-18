package com.cinema.rooms.controller;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.repository.RoomRepository;
import com.cinema.rooms.repository.SeatRepository;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.request.UpdateSeatRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    SeatRepository seatRepository;

    @BeforeEach
    void cleanUp() {
        seatRepository.deleteAll();
        roomRepository.deleteAll();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAll() throws Exception {
        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(1).seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andReturn();

        var rooms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room[].class));

        Assertions.assertEquals(1, rooms.size());
        Assertions.assertEquals(4, rooms.get(0).getSeats().size());
    }

    @Test
    void shouldGetAllWithBadRequest() throws Exception {
        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteRoom() throws Exception {
        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(1).seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(2).seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/room/delete/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andReturn();

        var rooms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room[].class));

        Assertions.assertEquals(1, rooms.size());
        Assertions.assertEquals(2, rooms.get(0).getRoomNumber());
        Assertions.assertEquals(4, rooms.get(0).getSeats().size());
    }

    @Test
    void shouldNotDeleteRoomBecauseNotExist() throws Exception {
        mockMvc.perform(delete("/room/delete/1")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetRoomByRoomNumber() throws Exception {
        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(2).seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/room/getRoomByRoomNumber/2"))
                .andExpect(status().isOk())
                .andReturn();

        var room = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room.class);

        Assertions.assertEquals(2, room.getRoomNumber());
        Assertions.assertEquals(4, room.getSeats().size());
    }

    @Test
    void shouldNotGetRoomByRoomNumber() throws Exception {
        mockMvc.perform(get("/room/getRoomByRoomNumber/2")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldChangeSeatReservation() throws Exception {
        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(1).seatsNumber(2).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/room/changeSeatReservation")
                        .content(objectMapper.writeValueAsBytes(UpdateSeatRequest.builder()
                                        .seatNumber(1)
                                        .roomNumber(1)
                                        .isBocked(true)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andReturn();

        var rooms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room[].class));

        Assertions.assertEquals(1, rooms.size());
        Assertions.assertEquals(true, rooms.get(0).getSeats().get(0).getIsBocked());
        Assertions.assertEquals(false, rooms.get(0).getSeats().get(1).getIsBocked());
    }

    @Test
    void shouldNotChangeSeatReservationBecauseTheSameStatus() throws Exception {
        mockMvc.perform(post("/room")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(1).seatsNumber(2).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/room/changeSeatReservation")
                        .content(objectMapper.writeValueAsBytes(UpdateSeatRequest.builder()
                                .seatNumber(1)
                                .roomNumber(1)
                                .isBocked(false)
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andReturn();

        var rooms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room[].class));

        Assertions.assertEquals(1, rooms.size());
        Assertions.assertEquals(false, rooms.get(0).getSeats().get(0).getIsBocked());
        Assertions.assertEquals(false, rooms.get(0).getSeats().get(1).getIsBocked());
    }
}