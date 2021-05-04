package org.springext.security.jwt;

public interface UserAuthenticationRequest {

    String getPassword();

    String getUsername();
}
