package org.springext.security.jwt.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public interface UserRegistrationRequest {

    @Email
    @NotEmpty
    String getUsername();

    @NotEmpty
    String getPassword();

    @NotEmpty
    String getPassword2();
}
