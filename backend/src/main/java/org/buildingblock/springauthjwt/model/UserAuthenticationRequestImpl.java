package org.buildingblock.springauthjwt.model;

import org.buildingblock.springauthjwt.service.UserAuthenticationRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserAuthenticationRequestImpl implements UserAuthenticationRequest {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
