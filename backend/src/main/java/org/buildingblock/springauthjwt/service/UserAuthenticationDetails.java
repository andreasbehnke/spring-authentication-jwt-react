package org.buildingblock.springauthjwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthenticationDetails extends UserDetails {

    String getUserKey();
}
