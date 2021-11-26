package org.springext.security.jwt.userdetails;

import org.springext.security.jwt.dto.UserRegistrationRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserAuthenticationDetailsService<U extends UserAuthenticationDetails, R extends UserRegistrationRequest> extends UserDetailsService {

    U loadUserByUsername(String username) throws UsernameNotFoundException;

    U loadUserByKey(String userKey) throws UsernameNotFoundException;

    @SuppressWarnings("unchecked")
    default U getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof UserAuthenticationDetails)) {
            throw new AuthenticationServiceException("Could not retrieve authorized user before authentication completed!");
        }
        return (U)authentication.getPrincipal();
    }

    boolean exists(@Valid R userRegistrationRequest);

    ConfirmationTicketInfo registerNewUser(@Valid R userRegistrationRequest, String encodedPassword);

    Optional<U> confirmRegistrationTicket(@NotNull String ticketId);

    Optional<ConfirmationTicketInfo> createForgotPasswordTicket(@NotNull String username);
}
