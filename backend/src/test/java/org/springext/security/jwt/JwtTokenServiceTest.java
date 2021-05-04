package org.springext.security.jwt;

import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenServiceTest {

    @Test
    public void testGenerateToken() {
        String userId = UUID.randomUUID().toString();
        JwtTokenService jwtTokenService = new JwtTokenService("abcde", 60, "example.com", NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(userId));
        JwtDetails details = jwtTokenService.getTokenDetails(token);
        assertNotNull(details);
        assertEquals(userId, details.getUserKey());
    }

    @Test
    public void testExpirationDate() {
        JwtTokenService jwtTokenService = new JwtTokenService("abcde", 0, "example.com", NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(UUID.randomUUID().toString()));
        assertNull(jwtTokenService.getTokenDetails(token));
    }

    @Test
    public void testWrongSigningKeyDate() {
        JwtTokenService jwtTokenService = new JwtTokenService("abcde", 60, "example.com", NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(UUID.randomUUID().toString()));
        jwtTokenService = new JwtTokenService("edcba", 60, "example.com", NOPLogger.NOP_LOGGER);
        assertNull(jwtTokenService.getTokenDetails(token));
    }
}
