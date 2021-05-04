package org.springext.security.jwt;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserAuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final Logger logger;

    public UserAuthenticationService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, Logger logger) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.logger = logger;
    }

    public <V> ResponseEntity<V> authenticateUser(UserAuthenticationRequest request, Function<UserAuthenticationDetails, V> mapToResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserAuthenticationDetails authDetails = (UserAuthenticationDetails) authentication.getPrincipal();
            String token = jwtTokenService.generateAccessToken(new JwtDetails(authDetails.getUserKey()));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(mapToResponse.apply(authDetails));
        } catch (BadCredentialsException ex) {
            logger.warn("Bad credentials provided by user");
        } catch (Exception ex) {
            logger.error("Could not authenticate user", ex);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
