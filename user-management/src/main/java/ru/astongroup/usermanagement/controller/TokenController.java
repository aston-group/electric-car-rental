package ru.astongroup.usermanagement.controller;

import org.springframework.web.bind.annotation.*;

import ru.astongroup.usermanagement.components.JwtTokenProvider;
import ru.astongroup.usermanagement.models.TokenArchive;
import ru.astongroup.usermanagement.services.TokenService;
import ru.astongroup.usermanagement.utils.mapper.UserMapper;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;
    private final JwtTokenProvider tokenProvider;
    public TokenController(TokenService tokenService, JwtTokenProvider TokenProvider) {
        this.tokenService = tokenService;
        this.tokenProvider = TokenProvider;
    }

    @GetMapping
    public TokenArchive getToken(@RequestBody String token) {

        return tokenService.findTokenIfExist(token);
    }

    @GetMapping("/isvalid")
    public Boolean isTokenValid(@RequestBody String token) {

        var user = tokenService.findUserByToken(token);
        return tokenProvider.validateToken(token, UserMapper.mapToDto(user));
    }
}
