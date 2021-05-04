package org.buildingblock.springauthjwt.model;

import org.buildingblock.springauthjwt.entities.User;
import org.buildingblock.springauthjwt.service.UserAuthenticationDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthenticationDetailsImpl implements UserAuthenticationDetails {

    private final User user;

    public UserAuthenticationDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getUserKey() {
        return user.getId().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
