package org.buildingblock.springauthjwt.service;

import org.buildingblock.springauthjwt.model.UserAuthenticationDetailsImpl;
import org.buildingblock.springauthjwt.service.repositories.UserRepository;
import org.springext.security.jwt.userdetails.UserAuthenticationDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class UserService implements UserAuthenticationDetailsService<UserAuthenticationDetailsImpl> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAuthenticationDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "username must not be null");
        return userRepository.findByEmail(username)
                .map(UserAuthenticationDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Override
    public UserAuthenticationDetailsImpl loadUserByKey(String userKey) throws UsernameNotFoundException {
        Assert.notNull(userKey, "userKey must not be null");
        return userRepository.findById(UUID.fromString(userKey))
                .map(UserAuthenticationDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userKey + " not found"));
    }
}
