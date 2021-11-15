package com.cinema.rooms.controller;

import com.cinema.rooms.entity.Room;
import com.cinema.rooms.entity.Seat;
import com.cinema.rooms.repository.RoomRepository;
import com.cinema.rooms.repository.SeatRepository;
import com.cinema.rooms.request.CreateRoomRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RoomRepository roomRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAll() throws Exception {

        List<Seat> seats = Arrays.asList(new Seat(), new Seat(), new Seat(), new Seat());
        Mockito.when(roomRepository.findAll()).thenReturn(List.of(Room.builder().roomNumber(1).seatsNumber(4).seats(seats).build()));

        MvcResult mvcResult = mockMvc.perform(get("/rooms/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        var rooms = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Room[].class));

        Assertions.assertEquals(1, rooms.size());
        Assertions.assertEquals(4, rooms.get(0).getSeats().size());
    }
}