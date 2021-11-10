package com.cinema.reservation.response;

import com.cinema.reservation.entity.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponse {

    @JsonIgnore
    private Long id;

    private String name;

    private String surname;

    private String email;

    private boolean isPaid;

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.surname = client.getSurname();
        this.name = client.getName();
        this.email = client.getEmail();
        this.isPaid = client.isPaid();
    }
}