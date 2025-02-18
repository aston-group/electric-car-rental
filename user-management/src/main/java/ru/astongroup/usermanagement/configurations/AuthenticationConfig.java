package ru.astongroup.usermanagement.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.astongroup.usermanagement.components.CustomAuthenticationManager;

@Configuration
public class AuthenticationConfig {

    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager();
    }
}