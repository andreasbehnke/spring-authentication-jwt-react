package org.springext.security.jwt.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("authentication.jwt")
public class JwtConfigurationProperties {

    private String signingKey;

    private int expirationTimeInSeconds;

    private String issuer;

    private boolean useCookie;

    private String cookieName;

    public JwtConfigurationProperties() { }

    public JwtConfigurationProperties(String signingKey, int expirationTimeInSeconds,
                                      String issuer, boolean useCookie, String cookieName) {
        this.signingKey = signingKey;
        this.expirationTimeInSeconds = expirationTimeInSeconds;
        this.issuer = issuer;
        this.useCookie = useCookie;
        this.cookieName = cookieName;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public int getExpirationTimeInSeconds() {
        return expirationTimeInSeconds;
    }

    public void setExpirationTimeInSeconds(int expirationTimeInSeconds) {
        this.expirationTimeInSeconds = expirationTimeInSeconds;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
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
