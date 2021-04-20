package org.buildingblock.springauthjwt.service;

import org.buildingblock.springauthjwt.model.UserAuthenticationDetails;
import org.buildingblock.springauthjwt.service.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new UserAuthenticationDetails(user))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }
}
