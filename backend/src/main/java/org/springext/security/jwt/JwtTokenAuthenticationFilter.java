package org.springext.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenService jwtTokenService;

    private boolean autoRefreshToken = false;

    public JwtTokenAuthenticationFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            JwtTokenService jwtTokenService,
            AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher);
        this.jwtTokenService = jwtTokenService;
        setAuthenticationManager(authenticationManager);
    }

    public void setAutoRefreshToken(boolean autoRefreshToken) {
        this.autoRefreshToken = autoRefreshToken;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JwtDetails jwtDetails = jwtTokenService.getTokenDetails(request);
        Authentication authentication = getAuthenticationManager().authenticate(new JwtAuthenticationToken(jwtDetails));
        if (autoRefreshToken) {
            jwtTokenService.setToken(response, jwtDetails);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // delegate filter processing to servlet filter chain after successful authentication
        chain.doFilter(request, response);
    }
}
