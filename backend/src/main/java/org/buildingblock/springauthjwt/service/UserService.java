package org.buildingblock.springauthjwt.service;

import org.buildingblock.springauthjwt.entities.User;
import org.buildingblock.springauthjwt.entities.UserTicket;
import org.buildingblock.springauthjwt.entities.UserTicketType;
import org.buildingblock.springauthjwt.model.UserAuthenticationDetailsImpl;
import org.buildingblock.springauthjwt.model.UserRegistrationRequestImpl;
import org.buildingblock.springauthjwt.service.repositories.UserRepository;
import org.buildingblock.springauthjwt.service.repositories.UserTicketRepository;
import org.springext.security.jwt.userdetails.ConfirmationTicketInfo;
import org.springext.security.jwt.userdetails.UserAuthenticationDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@Service
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
        userTicket.setEmail(userRegistrationRequest.getUsername());
        userTicket.setTicketType(UserTicketType.confirmRegistration);
        userTicket = userTicketRepository.save(userTicket);
        return new ConfirmationTicketInfo(
                userTicket.getId().toString(),
                user.getEmail());
    }

    @Override
    public Optional<UserAuthenticationDetailsImpl> confirmRegistrationTicket(String ticketId) {
        UUID id;
        try {
            id = UUID.fromString(ticketId);
        } catch (IllegalArgumentException iae) {
            return Optional.empty();
        }
        Optional<UserTicket> ticket = userTicketRepository.findById(id);
        if (ticket.isPresent()) {
            String email = ticket.get().getEmail();
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                user.get().setEnabled(true);
                userRepository.save(user.get());
                userTicketRepository.deleteById(id);
                return Optional.of(new UserAuthenticationDetailsImpl(user.get()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ConfirmationTicketInfo> createForgotPasswordTicket(String username) {
        Optional<User> user =  userRepository.findByEmail(username);
        if (user.isPresent()) {
            UserTicket userTicket = new UserTicket();
            String email = user.get().getEmail();
            userTicket.setEmail(email);
            userTicket.setTicketType(UserTicketType.forgotPassword);
            userTicket = userTicketRepository.save(userTicket);
            return Optional.of(new ConfirmationTicketInfo(
                    userTicket.getId().toString(),
                    email));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean resetPassword(String ticketId, String encodedPassword) {
        UUID id;
        try {
            id = UUID.fromString(ticketId);
        } catch (IllegalArgumentException iae) {
            return false;
        }
        Optional<UserTicket> ticket = userTicketRepository.findById(id);
        if (ticket.isPresent()) {
            String email = ticket.get().getEmail();
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                user.get().setHashedPassword(encodedPassword);
                userRepository.save(user.get());
                userTicketRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
