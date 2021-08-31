package org.buildingblock.springauthjwt.model;

import org.springext.security.jwt.dto.UserRegistrationRequest;

import javax.validation.constraints.Email;

public class UserRegistrationRequestImpl implements UserRegistrationRequest {

    @Email
    private String username;

    private String password;

    private String password2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
