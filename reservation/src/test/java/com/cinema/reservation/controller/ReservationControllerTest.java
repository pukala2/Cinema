package com.cinema.reservation.controller;

import com.cinema.reservation.client.MoviesFeignClient;
import com.cinema.reservation.client.RoomsFeignClient;
import com.cinema.reservation.client.model.*;
import com.cinema.reservation.entity.Client;
import com.cinema.reservation.repository.ClientRepository;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.CreateReservationRequest;
import com.cinema.reservation.request.CreateSeatRequest;
import com.cinema.reservation.request.DeleteReservationRequest;
import com.cinema.reservation.response.ClientResponse;
import com.cinema.reservation.response.ReservationResponse;
import com.cinema.reservation.utils.ReservationCodeGenerator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomsFeignClient roomsFeignClient;

    @MockBean
    private MoviesFeignClient moviesFeignClient;

    @MockBean
    private ReservationCodeGenerator reservationCodeGenerator;

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
    private final static String RESERVATION_CODE_A = "AAAAAAAA";
    private final static String RESERVATION_CODE_B = "BBBBBBBB";

    private final static Client CLIENT_JOHN = Client.builder()
            .email("john.snow@gmail.com")
            .name("John")
            .surname("Snow")
            .isPaid(true)
            .build();

    private final static Client CLIENT_ANNA = Client.builder()
            .email("anna.kowalska@gmail.com")
            .name("Anna")
            .surname("Kowalska")
            .isPaid(true)
            .build();

    private Room createRoomWithThreeSeats(int roomNumber) {
        return Room.builder().roomNumber(roomNumber).seatsNumber(3).seats(Arrays.asList(
                SeatResponse.builder().roomNumber(roomNumber).seatNumber(1).isBocked(false).build(),
                SeatResponse.builder().roomNumber(roomNumber).seatNumber(2).isBocked(false).build(),
                SeatResponse.builder().roomNumber(roomNumber).seatNumber(3).isBocked(false).build())).build();
    }

//    @Test
//    void shouldCreateReservation() throws Exception {
//        Mockito.when(roomsFeignClient.getRoomByRoomNumber(1)).thenReturn(createRoomWithThreeSeats(1));
//        Mockito.when(roomsFeignClient.changeSeatReservation(any())).thenReturn(null);
//        Mockito.when(reservationCodeGenerator.generateCode()).thenReturn(RESERVATION_CODE_A).thenReturn(RESERVATION_CODE_B);
//        Mockito.when(moviesFeignClient.getByTitle(new FindMovieRequest(MOVIE_TITLE))).thenReturn(new MovieResponse());
//
//        mockMvc.perform(post("/reservation/reserve")
//                .content(objectMapper.writeValueAsBytes(CreateReservationRequest.builder()
//                        .createSeatRequests(Arrays.asList(
//                                CreateSeatRequest.builder().roomNumber(1).seatNumber(1).build(),
//                                CreateSeatRequest.builder().roomNumber(1).seatNumber(2).build()))
//                        .movieTitle(MOVIE_TITLE)
//                        .client(CLIENT_JOHN)
//                        .build()))
//                .contentType("application/json"))
//                .andExpect(status().isCreated());
//
//        MvcResult mvcResult = mockMvc.perform(get("/reservation/getAll"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var reservations = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReservationResponse[].class));
//
//        Assertions.assertEquals(2, reservations.size());
//
//        Assertions.assertEquals(1, reservations.get(0).getRoomNumber());
//        Assertions.assertEquals(1, reservations.get(0).getSeatNumber());
//        Assertions.assertEquals(RESERVATION_CODE_A, reservations.get(0).getReservationCode());
//        Assertions.assertEquals(MOVIE_TITLE, reservations.get(0).getMovieTitle());
//
//        Assertions.assertEquals(1, reservations.get(1).getRoomNumber());
//        Assertions.assertEquals(2, reservations.get(1).getSeatNumber());
//        Assertions.assertEquals(RESERVATION_CODE_B, reservations.get(1).getReservationCode());
//        Assertions.assertEquals(MOVIE_TITLE, reservations.get(1).getMovieTitle());
//    }
//
//    @Test
//    void shouldCancelReservation() throws Exception {
//        Mockito.when(roomsFeignClient.getRoomByRoomNumber(1)).thenReturn(createRoomWithThreeSeats(1));
//        Mockito.when(roomsFeignClient.changeSeatReservation(any())).thenReturn(null);
//        Mockito.when(reservationCodeGenerator.generateCode()).thenReturn(RESERVATION_CODE_A).thenReturn(RESERVATION_CODE_B);
//        Mockito.when(moviesFeignClient.getByTitle(new FindMovieRequest(MOVIE_TITLE)))
//                .thenReturn(new MovieResponse()).thenReturn(new MovieResponse());
//
//        mockMvc.perform(post("/reservation/reserve")
//                        .content(objectMapper.writeValueAsBytes(CreateReservationRequest.builder()
//                                .createSeatRequests(List.of(CreateSeatRequest.builder().roomNumber(1).seatNumber(1).build()))
//                                .movieTitle(MOVIE_TITLE).client(CLIENT_JOHN).build()))
//                        .contentType("application/json"))
//                .andExpect(status().isCreated());
//
//        mockMvc.perform(post("/reservation/reserve")
//                        .content(objectMapper.writeValueAsBytes(CreateReservationRequest.builder()
//                                .createSeatRequests(List.of(CreateSeatRequest.builder().roomNumber(1).seatNumber(3).build()))
//                                .movieTitle(MOVIE_TITLE).client(CLIENT_ANNA).build()))
//                        .contentType("application/json"))
//                .andExpect(status().isCreated());
//
//        DeleteReservationRequest deleteReq = new DeleteReservationRequest();
//        deleteReq.setReservationCode(RESERVATION_CODE_A);
//        mockMvc.perform(delete("/reservation/cancelReservation")
//                        .content(objectMapper.writeValueAsBytes(deleteReq))
//                        .contentType("application/json"))
//                .andExpect(status().isOk());
//
//        MvcResult mvcResult = mockMvc.perform(get("/reservation/getAll"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var reservations = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ReservationResponse[].class));
//
//        Assertions.assertEquals(1, reservations.size());
//        Assertions.assertEquals(1, reservations.get(0).getRoomNumber());
//        Assertions.assertEquals(3, reservations.get(0).getSeatNumber());
//        Assertions.assertEquals(MOVIE_TITLE, reservations.get(0).getMovieTitle());
//    }
//
//    @Test
//    void shouldGetClients() throws Exception {
//        Mockito.when(roomsFeignClient.getRoomByRoomNumber(1)).thenReturn(createRoomWithThreeSeats(1));
//        Mockito.when(roomsFeignClient.changeSeatReservation(any())).thenReturn(null);
//        Mockito.when(reservationCodeGenerator.generateCode()).thenReturn(RESERVATION_CODE_A).thenReturn(RESERVATION_CODE_B);
//        Mockito.when(moviesFeignClient.getByTitle(new FindMovieRequest(MOVIE_TITLE))).thenReturn(new MovieResponse());
//
//        mockMvc.perform(post("/reservation/reserve")
//                        .content(objectMapper.writeValueAsBytes(CreateReservationRequest.builder()
//                                .createSeatRequests(List.of(CreateSeatRequest.builder().roomNumber(1).seatNumber(1).build()))
//                                .movieTitle(MOVIE_TITLE).client(CLIENT_JOHN).build()))
//                        .contentType("application/json"))
//                .andExpect(status().isCreated());
//
//        MvcResult mvcResult = mockMvc.perform(get("/reservation/getClients"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var reservations = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClientResponse[].class));
//
//        Assertions.assertEquals(1, reservations.size());
//        Assertions.assertEquals(CLIENT_JOHN.getName(), reservations.get(0).getName());
//        Assertions.assertEquals(CLIENT_JOHN.getSurname(), reservations.get(0).getSurname());
//        Assertions.assertEquals(CLIENT_JOHN.getEmail(), reservations.get(0).getEmail());
//        Assertions.assertEquals(CLIENT_JOHN.isPaid(), reservations.get(0).isPaid());
//    }
}