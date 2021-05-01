package org.buildingblock.springauthjwt.model;

import java.util.UUID;

public class UserView {

    private final UUID id;

    private final String email;

    public UserView(UserAuthenticationDetails userAuthenticationDetails) {
        this.id = userAuthenticationDetails.getId();
        this.email = userAuthenticationDetails.getUsername();
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
