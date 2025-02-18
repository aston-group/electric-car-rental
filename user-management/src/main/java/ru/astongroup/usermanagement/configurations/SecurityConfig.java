package ru.astongroup.usermanagement.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import ru.astongroup.usermanagement.models.enums.UserStatus;
import ru.astongroup.usermanagement.components.JwtTokenProvider;
import ru.astongroup.usermanagement.repositories.TokenRepository;
import ru.astongroup.usermanagement.repositories.UserRepository;
import ru.astongroup.usermanagement.services.CustomUserDetailsService;
import ru.astongroup.usermanagement.components.CustomAuthenticationManager;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationManager customAuthenticationManager) throws Exception {

        String adminRole = UserStatus.ADMIN.toString();
        String userRole = UserStatus.USER.toString();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // маршрутизация авторизованнных / анонимных запросов
                        // пока разрешен доступ по всем EndPoint'ам
                        // без авторизации
                        // кроме /users/mail/{email}, но это условие отдельно в UserController
                        // прописано - для тестирования доступа по токену
                        .requestMatchers(HttpMethod.GET,"/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .addFilter(new AuthenticationFilter(customAuthenticationManager, new JwtTokenProvider(), userRepository, tokenRepository))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }
}