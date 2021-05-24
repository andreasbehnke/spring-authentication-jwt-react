package org.springext.security.jwt.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthenticationDetails extends UserDetails {

    String getUserKey();
}
