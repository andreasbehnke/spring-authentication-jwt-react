package org.buildingblock.springauthjwt.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "public")
public class UserTicket {

    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne(optional = false)
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
