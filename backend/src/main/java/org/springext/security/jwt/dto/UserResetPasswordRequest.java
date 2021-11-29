package org.springext.security.jwt.dto;

import javax.validation.constraints.NotEmpty;

public class UserResetPasswordRequest {

    @NotEmpty
    private String ticketId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String password2;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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
