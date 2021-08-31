package org.buildingblock.springauthjwt.service;

import org.buildingblock.springauthjwt.entities.User;
import org.buildingblock.springauthjwt.entities.UserTicket;
import org.buildingblock.springauthjwt.model.UserAuthenticationDetailsImpl;
import org.buildingblock.springauthjwt.model.UserRegistrationRequestImpl;
import org.buildingblock.springauthjwt.service.repositories.UserRepository;
import org.buildingblock.springauthjwt.service.repositories.UserTicketRepository;
import org.springext.security.jwt.dto.UserRegistrationRequest;
import org.springext.security.jwt.userdetails.ConfirmationTicketInfo;
import org.springext.security.jwt.userdetails.UserAuthenticationDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
public class UserService implements UserAuthenticationDetailsService<UserAuthenticationDetailsImpl, UserRegistrationRequestImpl> {

    private final UserRepository userRepository;

    private final UserTicketRepository userTicketRepository;

    public UserService(UserRepository userRepository, UserTicketRepository userTicketRepository) {
        this.userRepository = userRepository;
        this.userTicketRepository = userTicketRepository;
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

    @Override
    public boolean exists(UserRegistrationRequestImpl userRegistrationRequest) {
        return userRepository.existsByEmail(userRegistrationRequest.getUsername());
    }

    @Override
    public ConfirmationTicketInfo registerNewUser(UserRegistrationRequestImpl userRegistrationRequest, String encodedPassword) {
        User user = new User();
        user.setEmail(userRegistrationRequest.getUsername());
        user.setEnabled(false); // user will be enabled when the confirmation ticket will be redeemed
        user.setHashedPassword(encodedPassword);
        user = userRepository.save(user);
        UserTicket userTicket = new UserTicket();
        userTicket.setUser(user);
        userTicket = userTicketRepository.save(userTicket);
        return new ConfirmationTicketInfo(
                userTicket.getId().toString(),
                user.getEmail());
    }
}
