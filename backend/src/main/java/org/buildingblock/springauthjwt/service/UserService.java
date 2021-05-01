package org.buildingblock.springauthjwt.service;

import org.buildingblock.springauthjwt.model.UserAuthenticationDetails;
import org.buildingblock.springauthjwt.service.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserAuthenticationDetailsService<UserAuthenticationDetails, UUID> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAuthenticationDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserAuthenticationDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Override
    public UserAuthenticationDetails loadUserById(UUID id) {
        return userRepository.findById(id).map(UserAuthenticationDetails::new).orElse(null);
    }

    @Override
    public UserAuthenticationDetails getAuthorizedUser() {
        return null;
    }
}
