package org.springext.security.jwt.service;

import org.junit.jupiter.api.Test;
import org.slf4j.helpers.NOPLogger;
import org.springext.security.jwt.service.JwtConfigurationProperties;
import org.springext.security.jwt.service.JwtDetails;
import org.springext.security.jwt.service.JwtTokenService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenServiceTest {

    @Test
    public void testGenerateToken() {
        String userId = UUID.randomUUID().toString();
        JwtConfigurationProperties properties = new JwtConfigurationProperties("abcde", 60,
                "example.com", false, null);
        JwtTokenService jwtTokenService = new JwtTokenService(properties, NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(userId));
        JwtDetails details = jwtTokenService.getTokenDetails(token);
        assertNotNull(details);
        assertEquals(userId, details.getUserKey());
    }

    @Test
    public void testExpirationDate() {
        JwtConfigurationProperties properties = new JwtConfigurationProperties("abcde", 0,
                "example.com", false, null);
        JwtTokenService jwtTokenService = new JwtTokenService(properties, NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(UUID.randomUUID().toString()));
        assertNull(jwtTokenService.getTokenDetails(token));
    }

    @Test
    public void testWrongSigningKeyDate() {
        JwtConfigurationProperties properties = new JwtConfigurationProperties("abcde", 60,
                "example.com", false, null);
        JwtTokenService jwtTokenService = new JwtTokenService(properties, NOPLogger.NOP_LOGGER);
        String token = jwtTokenService.generateAccessToken(new JwtDetails(UUID.randomUUID().toString()));
        properties = new JwtConfigurationProperties("edcba", 60,
                "example.com", false, null);
        jwtTokenService = new JwtTokenService(properties, NOPLogger.NOP_LOGGER);
        assertNull(jwtTokenService.getTokenDetails(token));
    }
}
