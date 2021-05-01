package org.buildingblock.springauthjwt.boundary;

import org.buildingblock.springauthjwt.model.JwtDetails;
import org.buildingblock.springauthjwt.model.UserAuthenticationDetails;
import org.buildingblock.springauthjwt.model.UserAuthenticationRequest;
import org.buildingblock.springauthjwt.model.UserView;
import org.buildingblock.springauthjwt.service.JwtTokenService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/authentication")
public class UserAuthenticationResource {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final Logger logger;

    public UserAuthenticationResource(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, Logger logger) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.logger = logger;
    }

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid UserAuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserAuthenticationDetails authDetails = (UserAuthenticationDetails) authentication.getPrincipal();
            String token = jwtTokenService.generateAccessToken(new JwtDetails(authDetails.getId()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(new UserView(authDetails));
        } catch (BadCredentialsException ex) {
            logger.warn("Bad credentials provided by user");
        } catch (Exception ex) {
            logger.error("Could not authenticate user", ex);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
