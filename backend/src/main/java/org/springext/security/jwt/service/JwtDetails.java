package org.springext.security.jwt.service;

/**
 * Holds all information about a valid JSON Web Token.
 */
public class JwtDetails {

    private final String userKey;

    public JwtDetails(String userKey) {
        this.userKey = userKey;
    }

    public String getUserKey() {
        return userKey;
    }
}
