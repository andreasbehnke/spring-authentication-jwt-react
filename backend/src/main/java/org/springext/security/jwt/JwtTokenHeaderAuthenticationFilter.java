package org.springext.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.split;

@Component
public class JwtTokenHeaderAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UserAuthenticationDetailsService userService;

    public JwtTokenHeaderAuthenticationFilter(JwtTokenService jwtTokenService, UserAuthenticationDetailsService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!hasText(header) || !header.startsWith("Bearer ")) {
            // if header is missing or not correct
            filterChain.doFilter(request, response);
            return;
        }

        String token =  split(header, " ")[1];
        JwtDetails jwtDetails = jwtTokenService.getTokenDetails(token);
        if (jwtDetails == null) {
            // could not extract jwt details
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = userService.loadUserByKey(jwtDetails.getUserKey());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails == null ? Collections.EMPTY_LIST : userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
