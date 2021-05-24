package org.springext.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.buildingblock.springauthjwt.model.UserAuthenticationRequestImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenService jwtTokenService;

    private final ObjectMapper objectMapper;

    public JsonUsernamePasswordAuthenticationFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            ObjectMapper objectMapper,
            JwtTokenService jwtTokenService,
            AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher);
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            UserAuthenticationRequestImpl userAuthenticationRequest = objectMapper.readValue(request.getInputStream(), UserAuthenticationRequestImpl.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    userAuthenticationRequest.getUsername(), userAuthenticationRequest.getPassword());
            Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);
            UserAuthenticationDetails authDetails = (UserAuthenticationDetails) authentication.getPrincipal();
            jwtTokenService.setTokenToHeader(response, new JwtDetails(authDetails.getUserKey()));
            objectMapper.writeValue(response.getOutputStream(), new UserView(authDetails.getUserKey(), authDetails.getUsername()));
            return authentication;
        } catch (IOException e) {
            throw new AuthenticationServiceException("Could not read JSON object from input stream", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // do nothing, simply return with authorization header or cookie
    }
}
