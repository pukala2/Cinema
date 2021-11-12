package com.cinema.reservation.request;

import com.cinema.reservation.entity.Client;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateReservationRequest {

    @NotEmpty(message = "Seat requests can not be empty")
    @NotNull(message = "Seat requests can not be null")
    private List<CreateSeatRequest> createSeatRequests;

    @NotNull(message = "Movie title can not be empty")
    private String movieTitle;

    @NotNull(message = "Client can not be empty")
    private Client client;

    public Client getClient() {
        Client client = new Client();
        client.setReservation(this.client.getReservation());
        client.setEmail(this.client.getEmail());
        client.setName(this.client.getName());
        client.setSurname(this.client.getSurname());
        client.setPaid(this.client.isPaid());
        return client;
    }
}
