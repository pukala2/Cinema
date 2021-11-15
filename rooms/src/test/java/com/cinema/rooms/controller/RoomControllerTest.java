package com.cinema.rooms.controller;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.repository.RoomRepository;
import com.cinema.rooms.repository.SeatRepository;
import com.cinema.rooms.request.CreateRoomRequest;
import com.cinema.rooms.response.RoomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void testGetAll() throws Exception {
        mockMvc.perform(post("/rooms/create")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().roomNumber(1).seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/rooms/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        var rooms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room[].class));

        Assertions.assertEquals(1, rooms.size());
        Assertions.assertEquals(4, rooms.get(0).getSeats().size());
    }

    @Test
    void testGetAllWithBadRequest() throws Exception {
        mockMvc.perform(post("/rooms/create")
                        .content(objectMapper.writeValueAsBytes(CreateRoomRequest.builder().seatsNumber(4).build()))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}