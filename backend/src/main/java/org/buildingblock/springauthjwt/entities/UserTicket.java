package org.buildingblock.springauthjwt.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(schema = "public")
public class UserTicket {

    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private UserTicketType ticketType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserTicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(UserTicketType ticketType) {
        this.ticketType = ticketType;
    }
}
