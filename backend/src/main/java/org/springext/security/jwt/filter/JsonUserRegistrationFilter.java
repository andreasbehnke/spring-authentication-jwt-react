package org.springext.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springext.security.jwt.dto.UserRegistrationRequest;
import org.springext.security.jwt.dto.UserRegistrationResult;
import org.springext.security.jwt.dto.UserRegistrationResultMessage;
import org.springext.security.jwt.userdetails.ConfirmationTicketInfo;
import org.springext.security.jwt.userdetails.ConfirmationTicketService;
import org.springext.security.jwt.userdetails.UserAuthenticationDetails;
import org.springext.security.jwt.userdetails.UserAuthenticationDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

public class JsonUserRegistrationFilter<U extends UserAuthenticationDetails, R extends UserRegistrationRequest> extends GenericFilterBean {

    private final RequestMatcher requestMatcher;

    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserAuthenticationDetailsService<U,R> userAuthenticationDetailsService;

    private final ConfirmationTicketService confirmationTicketService;

    private final Class<R> classOfUserRegistrationRequest;

    public JsonUserRegistrationFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper, PasswordEncoder passwordEncoder,
                                      UserAuthenticationDetailsService<U, R> userAuthenticationDetailsService, ConfirmationTicketService confirmationTicketService,
                                      Class<R> classOfUserRegistrationRequest) {
        this.requestMatcher = requestMatcher;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationDetailsService = userAuthenticationDetailsService;
        this.confirmationTicketService = confirmationTicketService;
        this.classOfUserRegistrationRequest = classOfUserRegistrationRequest;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (requestMatcher.matches(request) && request.getMethod().equals("POST")) {
            R userAuthenticationRequest = objectMapper.readValue(request.getInputStream(), classOfUserRegistrationRequest);
            UserRegistrationResult result = registerNewUser(userAuthenticationRequest);
            if (result.getMessage() != UserRegistrationResultMessage.OK) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }
            objectMapper.writeValue(response.getOutputStream(), result);
        } else {
            chain.doFilter(request, response);
        }
    }

    private UserRegistrationResult registerNewUser(R userAuthenticationRequest) {
        if (userAuthenticationRequest.getPassword() == null
                || !userAuthenticationRequest.getPassword().equals(userAuthenticationRequest.getPassword2())) {
            return UserRegistrationResult.passwordDoNotMatch();
        }
        try {
            if (userAuthenticationDetailsService.exists(userAuthenticationRequest)) {
                return UserRegistrationResult.userExists();
            }
            ConfirmationTicketInfo confirmationTicketInfo =
                    userAuthenticationDetailsService.registerNewUser(userAuthenticationRequest, passwordEncoder.encode(userAuthenticationRequest.getPassword()));
            confirmationTicketService.sendConfirmationTicket(confirmationTicketInfo);
        } catch (ConstraintViolationException cve) {
            return UserRegistrationResult.invalidRegistration();
        }
        return UserRegistrationResult.ok();
    }
}
