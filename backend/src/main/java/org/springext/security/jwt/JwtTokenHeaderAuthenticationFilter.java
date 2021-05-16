package org.springext.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenHeaderAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenService jwtTokenService;

    private final UserAuthenticationDetailsService userService;

    public JwtTokenHeaderAuthenticationFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            JwtTokenService jwtTokenService,
            UserAuthenticationDetailsService userService,
            AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher);
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            throw new BadCredentialsException("JWT header or cookie is missing");
        }

        String token =  StringUtils.split(header, " ")[1];
        JwtDetails jwtDetails = jwtTokenService.getTokenDetails(token);
        if (jwtDetails == null) {
            throw new BadCredentialsException("JWT token could not be parsed");
        }
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(jwtDetails));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // delegate filter processing to servlet filter chain after successful authentication
        chain.doFilter(request, response);
    }
}
