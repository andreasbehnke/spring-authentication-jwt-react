package org.springext.security.jwt.authentication;

import org.springext.security.jwt.service.JwtDetails;
import org.springext.security.jwt.userdetails.UserAuthenticationDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserAuthenticationDetailsService detailsService;

    public JwtAuthenticationProvider(UserAuthenticationDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtDetails jwtDetails = ((JwtAuthenticationToken)authentication).getJwtDetails();
        return detailsService.loadUserByKey(jwtDetails.getUserKey());
    }
}
