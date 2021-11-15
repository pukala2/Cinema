package com.cinema.reservation.controller;

import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.Room;
import com.cinema.reservation.client.model.SeatResponse;
import com.cinema.reservation.entity.Client;
import com.cinema.reservation.repository.ClientRepository;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.CreateSeatRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomsFeignClient roomsFeignClient;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanUp() {
        reservationRepository.deleteAll();
        clientRepository.deleteAll();
    }

    private final static String MOVIE_TITLE = "EASY LIVE IN JAVA";
    private final static Client CLIENT = Client.builder()
            .email("john.snow@gmail.com")
            .name("John")
            .surname("Snow")
            .isPaid(true)
            .build();

    private Room createRoomWithThreeSeats(int roomNumber) {
        return Room.builder().roomNumber(roomNumber).seatsNumber(3).seats(Arrays.asList(
                SeatResponse.builder()
                        .roomNumber(roomNumber)
                        .seatNumber(1)
                        .isBocked(false)
                        .build(),
                SeatResponse.builder()
                        .roomNumber(roomNumber)
                        .seatNumber(2)
                        .isBocked(false)
                        .build(),
                SeatResponse.builder()
                        .roomNumber(roomNumber)
                        .seatNumber(3)
                        .isBocked(false)
                        .build())).build();
    }

    @Test
    void shouldCreateReservation() throws Exception {
        Mockito.when(roomsFeignClient.getRoomByRoomNumber(1)).thenReturn(createRoomWithThreeSeats(1));
        Mockito.when(roomsFeignClient.changeSeatReservation(any())).thenReturn(null);

        mockMvc.perform(post("/reservation/reserve")
                .content(objectMapper.writeValueAsBytes(CreateReservationRequest.builder()
                        .createSeatRequests(Arrays.asList(
                                CreateSeatRequest.builder().roomNumber(1).seatNumber(1).build(),
                                CreateSeatRequest.builder().roomNumber(1).seatNumber(2).build()))
                        .movieTitle(MOVIE_TITLE)
                        .client(CLIENT)
                        .build()))
                .contentType("application/json"))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/reservation/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        var reservations = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReservationResponse[].class));

        Assertions.assertEquals(2, reservations.size());
        Assertions.assertEquals(1, reservations.get(0).getRoomNumber());
        Assertions.assertEquals(1, reservations.get(0).getSeatNumber());
        Assertions.assertEquals(MOVIE_TITLE, reservations.get(0).getMovieTitle());
        Assertions.assertEquals(1, reservations.get(1).getRoomNumber());
        Assertions.assertEquals(2, reservations.get(1).getSeatNumber());
        Assertions.assertEquals(MOVIE_TITLE, reservations.get(1).getMovieTitle());
    }

}