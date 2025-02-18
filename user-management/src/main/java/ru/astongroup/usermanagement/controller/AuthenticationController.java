package ru.astongroup.usermanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ru.astongroup.usermanagement.components.JwtTokenProvider;
import ru.astongroup.usermanagement.models.UserModel;
import ru.astongroup.usermanagement.services.UserService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestHeader("Authorization") String authorizationHeader) {

        if(authorizationHeader != null && authorizationHeader.startsWith("Basic ")){

            String base64Credentials = authorizationHeader.substring("Basic ".length());
            String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
            String[] usernamePassword = credentials.split(":", 2);

            String username = usernamePassword[0];
            String password = usernamePassword[1];

            if (username == null || password == null)
                throw new IllegalArgumentException("Username or password is null");

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);

            if(authenticationToken.isAuthenticated()){

                UserModel userModel = userService.getByEmail(username);
                String token = jwtTokenProvider.getToken(userModel);

                return ResponseEntity.ok(token);
            }
            else {
                throw new IllegalArgumentException("Invalid username or password");
            }

        }
        else {
            throw new IllegalArgumentException("Basic Authorization header is missing");
        }
    }
}
