package org.buildingblock.springauthjwt.model;

import org.buildingblock.springauthjwt.entities.User;

import java.util.UUID;

public class UserView {

    private final UUID id;

    private final String email;

    public UserView(UserAuthenticationDetails userAuthenticationDetails) {
        this.id = userAuthenticationDetails.getId();
        this.email = userAuthenticationDetails.getUsername();
    }
}
