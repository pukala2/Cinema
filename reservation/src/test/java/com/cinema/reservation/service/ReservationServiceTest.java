package com.cinema.reservation.service;

import com.cinema.reservation.exception.NotExistingReservationCodeException;
import com.cinema.reservation.repository.ReservationRepository;
import com.cinema.reservation.request.DeleteReservationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService sut;

    @Mock
    ReservationRepository reservationRepository;

    @Test
    void shouldGiveExceptionWhenReservationWithCodeNotExist() {

        DeleteReservationRequest deleteReservationRequest = new DeleteReservationRequest();
        deleteReservationRequest.setReservationCode("aps12345");
        Mockito.when(reservationRepository.findByReservationCode(
                deleteReservationRequest.getReservationCode())).thenReturn(null);

        NotExistingReservationCodeException thrown = Assertions.assertThrows(
                NotExistingReservationCodeException.class, () -> {
            sut.remove(deleteReservationRequest);
        });

        Assertions.assertEquals("Reservation with this code does not exist",
                thrown.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getHttpStatus());
    }
}