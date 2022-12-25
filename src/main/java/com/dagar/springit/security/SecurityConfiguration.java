package com.dagar.springit.security;

import com.dagar.springit.service.UserDetailsServiceImpl;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = (UserDetailsServiceImpl) userDetailsService;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests(auth -> {
                    auth.requestMatchers(EndpointRequest.to("info")).permitAll();
                    auth.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN");
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/link/submit").hasRole("ADMIN");
                    auth.requestMatchers("/h2-console/**").permitAll();
                })
                .formLogin()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .and()
                .build();
    }
}
