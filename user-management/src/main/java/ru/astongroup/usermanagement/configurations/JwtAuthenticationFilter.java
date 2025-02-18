package ru.astongroup.usermanagement.configurations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.astongroup.usermanagement.components.JwtTokenProvider;
import ru.astongroup.usermanagement.models.UserModel;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String JwtSecret, String JwtExpiration, JwtTokenProvider jwtTokenProvider) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/users/login");
    }

    // метод из базового абстрактного класса AbstractAuthenticationProcessingFilter,
    // но IDE почему-то не даёт переопределить
    // ругается, что, дескать, нет такого метода у суперкласса... ну да, ну да, пошёл я нафиг...
    //@Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {

        String authorizationHeader = req.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Basic ")){

            String base64Credentials = authorizationHeader.substring("Basic ".length());
            String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
            String[] usernamePassword = credentials.split(":", 2);

            String username = usernamePassword[0];
            String password = usernamePassword[1];

            if (username == null || password == null) {
                throw new IllegalArgumentException("Username or password is null");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authenticationToken);
        }
        else {
            throw new IllegalArgumentException("Basic Authorization header is missing");
        }
    }

    // метод из базового абстрактного класса AbstractAuthenticationProcessingFilter
    //@Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserModel user = (UserModel) authResult.getPrincipal();

        String token = jwtTokenProvider.getToken(user);

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().write("Authentication successful. Token: " + token);
    }
}
