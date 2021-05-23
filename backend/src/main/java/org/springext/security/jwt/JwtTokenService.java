package org.springext.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Provides methods for generating and validating JSON Web Tokens.
 */
@Component
public class JwtTokenService {

    public static final String BEARER = "Bearer ";

    private final Logger logger;

    private final String jwtSigningKey;

    private final int jwtExpirationTimeInSeconds;

    private final String jwtIssuer;

    public JwtTokenService(
            @Value("${authentication.jwt.signingKey}") String jwtSigningKey,
            @Value("${authentication.jwt.expirationTimeInSeconds}") int jwtExpirationTimeInSeconds,
            @Value("${authentication.jwt.issuer}") String jwtIssuer,
            Logger logger) {
        this.jwtSigningKey = jwtSigningKey;
        this.jwtExpirationTimeInSeconds = jwtExpirationTimeInSeconds;
        this.jwtIssuer = jwtIssuer;
        this.logger = logger;
    }

    public JwtDetails getTokenDetails(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSigningKey)
                    .parseClaimsJws(token).getBody();
            return new JwtDetails(claims.getSubject());
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.warn("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        }
        return null;
    }

    public String generateAccessToken(JwtDetails details) {
        Assert.notNull(details, "Parameter details must not be null");
        return Jwts.builder()
                .setSubject(details.getUserKey())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTimeInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSigningKey)
                .compact();
    }

    public JwtDetails getTokenDetailsFromHeader(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            throw new BadCredentialsException("JWT header or cookie is missing");
        }

        String token =  StringUtils.split(header, " ")[1];
        JwtDetails jwtDetails = getTokenDetails(token);
        if (jwtDetails == null) {
            throw new BadCredentialsException("JWT token could not be parsed");
        }
        return jwtDetails;
    }

    public void setTokenToHeader(HttpServletResponse response, JwtDetails jwtDetails) {
        // this will reset token expiration
        String freshToken = generateAccessToken(jwtDetails);
        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + freshToken);
    }

    public ResponseEntity.BodyBuilder responseEntityWithAuthorizationHeader(JwtDetails jwtDetails) {
        String token = generateAccessToken(jwtDetails);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, BEARER + token);
    }
}
