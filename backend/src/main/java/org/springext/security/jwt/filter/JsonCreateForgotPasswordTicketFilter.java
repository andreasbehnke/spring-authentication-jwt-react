package org.springext.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springext.security.jwt.dto.UserForgotPasswordRequest;
import org.springext.security.jwt.userdetails.ConfirmationTicketInfo;
import org.springext.security.jwt.userdetails.ConfirmationTicketService;
import org.springext.security.jwt.userdetails.UserAuthenticationDetailsService;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JsonCreateForgotPasswordTicketFilter extends GenericFilterBean {

    private final RequestMatcher requestMatcher;

    private final ObjectMapper objectMapper;

    private final UserAuthenticationDetailsService<?,?> userAuthenticationDetailsService;

    private final ConfirmationTicketService confirmationTicketService;

    public JsonCreateForgotPasswordTicketFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper, UserAuthenticationDetailsService<?, ?> userAuthenticationDetailsService,
                                                ConfirmationTicketService confirmationTicketService) {
        this.requestMatcher = requestMatcher;
        this.objectMapper = objectMapper;
        this.userAuthenticationDetailsService = userAuthenticationDetailsService;
        this.confirmationTicketService = confirmationTicketService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (requestMatcher.matches(request) && request.getMethod().equals("POST")) {
            UserForgotPasswordRequest userForgotPasswordRequest = objectMapper.readValue(request.getInputStream(), UserForgotPasswordRequest.class);
            Optional<ConfirmationTicketInfo> confirmationTicketInfo = userAuthenticationDetailsService.createForgotPasswordTicket(userForgotPasswordRequest.getUsername());
            confirmationTicketInfo.ifPresent(confirmationTicketService::sendForgotPasswordTicket);
        } else {
            chain.doFilter(request, response);
        }
    }
}
