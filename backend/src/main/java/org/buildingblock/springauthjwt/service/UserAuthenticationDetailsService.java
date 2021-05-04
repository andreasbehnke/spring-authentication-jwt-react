package org.buildingblock.springauthjwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserAuthenticationDetailsService<U extends UserAuthenticationDetails> extends UserDetailsService {

    U loadUserByUsername(String username) throws UsernameNotFoundException;

    U loadUserByKey(String userKey);

    U getAuthorizedUser();
}
