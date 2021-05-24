package org.springext.security.jwt.authentication;

import org.springext.security.jwt.service.JwtDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final JwtDetails jwtDetails;

    public JwtAuthenticationToken(JwtDetails jwtDetails) {
        super(null, null);
        this.jwtDetails = jwtDetails;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public JwtDetails getJwtDetails() {
        return jwtDetails;
    }
}
