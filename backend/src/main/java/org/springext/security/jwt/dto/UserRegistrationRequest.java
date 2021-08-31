package org.springext.security.jwt.dto;

public interface UserRegistrationRequest {

    String getUsername();

    String getPassword();

    String getPassword2();
}
