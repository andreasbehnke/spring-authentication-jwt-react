package org.buildingblock.springauthjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.buildingblock.springauthjwt.service.UserService;
import org.springext.security.jwt.filter.JsonUsernamePasswordAuthenticationFilter;
import org.springext.security.jwt.authentication.JwtAuthenticationProvider;
import org.springext.security.jwt.filter.JwtTokenAuthenticationFilter;
import org.springext.security.jwt.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final JwtTokenService jwtTokenService;

    private final UserService userService;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final boolean autoRefreshToken;

    public SecurityConfig(ObjectMapper objectMapper, JwtTokenService jwtTokenService, UserService userService,
                          JwtAuthenticationProvider jwtAuthenticationProvider,
                          @Value("${authentication.jwt.autoRefreshToken}") boolean autoRefreshToken) {
        this.objectMapper = objectMapper;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.autoRefreshToken = autoRefreshToken;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authenticationProvider(jwtAuthenticationProvider);

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                        )
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // public endpoints
                .antMatchers("/public/**", "/error").permitAll()
                // private endpoints
                .anyRequest().authenticated();

        // Add JSON username password authentication filter
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter =
                new JsonUsernamePasswordAuthenticationFilter(
                        new AntPathRequestMatcher("/public/login"),
                        objectMapper,
                        jwtTokenService,
                        authenticationManagerBean()
                );
        http.addFilterAt(
                jsonUsernamePasswordAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        // Add JWT token filter
        JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter =
                new JwtTokenAuthenticationFilter(
                        new NegatedRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/public/**"), new AntPathRequestMatcher("/error"))),
                        jwtTokenService,
                        authenticationManagerBean());
        jwtTokenAuthenticationFilter.setAutoRefreshToken(autoRefreshToken);
        http.addFilterAfter(
                jwtTokenAuthenticationFilter,
                JsonUsernamePasswordAuthenticationFilter.class
        );
    }
}
