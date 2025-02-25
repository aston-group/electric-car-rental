package ru.astongroup.usermanagement.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.astongroup.usermanagement.components.CustomAuthenticationManager;

@Slf4j
@Configuration
public class AuthenticationConfig {

    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {

        log.info("-------------------------\nAuthenticationConfig.CustomAuthenticationManager bean created\n-------------------------\n");
        return new CustomAuthenticationManager();
    }
}