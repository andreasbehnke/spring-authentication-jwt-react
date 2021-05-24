package org.springext.security.jwt.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("authentication.jwt")
public class JwtConfigurationProperties {

    private String jwtSigningKey;

    private int jwtExpirationTimeInSeconds;

    private String jwtIssuer;

    private boolean useCookie;

    private String cookieName;

    public JwtConfigurationProperties() { }

    public JwtConfigurationProperties(String jwtSigningKey, int jwtExpirationTimeInSeconds,
                                      String jwtIssuer, boolean useCookie, String cookieName) {
        this.jwtSigningKey = jwtSigningKey;
        this.jwtExpirationTimeInSeconds = jwtExpirationTimeInSeconds;
        this.jwtIssuer = jwtIssuer;
        this.useCookie = useCookie;
        this.cookieName = cookieName;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public int getJwtExpirationTimeInSeconds() {
        return jwtExpirationTimeInSeconds;
    }

    public void setJwtExpirationTimeInSeconds(int jwtExpirationTimeInSeconds) {
        this.jwtExpirationTimeInSeconds = jwtExpirationTimeInSeconds;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public void setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}
