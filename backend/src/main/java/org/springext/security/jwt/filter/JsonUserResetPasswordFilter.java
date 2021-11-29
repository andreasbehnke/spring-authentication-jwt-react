package org.springext.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springext.security.jwt.dto.UserRegistrationResult;
import org.springext.security.jwt.dto.UserRegistrationResultMessage;
import org.springext.security.jwt.dto.UserResetPasswordRequest;
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

public class JsonUserResetPasswordFilter extends GenericFilterBean {

    private final RequestMatcher requestMatcher;

    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserAuthenticationDetailsService<?,?> userAuthenticationDetailsService;

    public JsonUserResetPasswordFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper, PasswordEncoder passwordEncoder,
                                       UserAuthenticationDetailsService<?, ?> userAuthenticationDetailsService) {
        this.requestMatcher = requestMatcher;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationDetailsService = userAuthenticationDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (requestMatcher.matches(request) && request.getMethod().equals("POST")) {
            UserResetPasswordRequest userAuthenticationRequest = objectMapper.readValue(request.getInputStream(), UserResetPasswordRequest.class);
            UserRegistrationResult result = resetPassword(userAuthenticationRequest);
            if (result.getMessage() != UserRegistrationResultMessage.OK) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }
            objectMapper.writeValue(response.getOutputStream(), result);
        } else {
            chain.doFilter(request, response);
        }
    }

    private UserRegistrationResult resetPassword(UserResetPasswordRequest userResetPasswordRequest) {
        if (userResetPasswordRequest.getPassword() == null
                || !userResetPasswordRequest.getPassword().equals(userResetPasswordRequest.getPassword2())) {
            return UserRegistrationResult.passwordDoNotMatch();
        }
        try {
            String encodedPassword = passwordEncoder.encode(userResetPasswordRequest.getPassword());
            if (!userAuthenticationDetailsService.resetPassword(userResetPasswordRequest.getTicketId(), encodedPassword)) {
                return UserRegistrationResult.invalidPasswordResetTicket();
            }
        } catch (ConstraintViolationException cve) {
            return UserRegistrationResult.invalidPasswordReset();
        }
        return UserRegistrationResult.ok();
    }
}
