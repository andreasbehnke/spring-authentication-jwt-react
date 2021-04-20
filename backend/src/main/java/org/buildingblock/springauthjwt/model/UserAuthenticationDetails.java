package org.buildingblock.springauthjwt.model;

import org.buildingblock.springauthjwt.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthenticationDetails implements UserDetails {

    private final User user;

    public UserAuthenticationDetails(User user) {
        this.user = user;
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
