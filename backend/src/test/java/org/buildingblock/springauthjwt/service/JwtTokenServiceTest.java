package org.buildingblock.springauthjwt.service;

import org.buildingblock.springauthjwt.model.JwtDetails;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenServiceTest {

    @Test
    public void testGenerateToken() {
        UUID userId = UUID.randomUUID();
        JwtTokenService jwtTokenService = new JwtTokenService("abcde", 60, "example.com", NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(userId));
        JwtDetails details = jwtTokenService.getTokenDetails(token);
        assertNotNull(details);
        assertEquals(userId, details.getUserId());
    }

    @Test
    public void testExpirationDate() {
        JwtTokenService jwtTokenService = new JwtTokenService("abcde", 0, "example.com", NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(UUID.randomUUID()));
        assertNull(jwtTokenService.getTokenDetails(token));
    }

    @Test
    public void testWrongSigningKeyDate() {
        JwtTokenService jwtTokenService = new JwtTokenService("abcde", 60, "example.com", NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(UUID.randomUUID()));
        jwtTokenService = new JwtTokenService("edcba", 60, "example.com", NOPLogger.NOP_LOGGER);
        assertNull(jwtTokenService.getTokenDetails(token));
    }
}
