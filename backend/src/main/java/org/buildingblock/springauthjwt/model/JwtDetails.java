package org.buildingblock.springauthjwt.model;

import java.util.UUID;

/**
 * Holds all information about a valid JSON Web Token.
 */
public class JwtDetails {

    private final UUID userId;

    public JwtDetails(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
