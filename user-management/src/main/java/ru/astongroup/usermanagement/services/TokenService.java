package ru.astongroup.usermanagement.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;
import ru.astongroup.usermanagement.components.JwtTokenProvider;
import ru.astongroup.usermanagement.models.TokenArchive;
import ru.astongroup.usermanagement.models.UserModel;
import ru.astongroup.usermanagement.repositories.UserRepository;
import ru.astongroup.usermanagement.utils.StaticResources;
import ru.astongroup.usermanagement.repositories.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    public TokenService(TokenRepository tokenRepository, JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }


    public TokenArchive saveToken(String token, long userId, String userEmail) {
        TokenArchive tokenArchive = new TokenArchive();

        Date expired = setExpirationDate();

        tokenArchive.setToken(token);
        tokenArchive.setUserid(userId);
        tokenArchive.setUsername(userEmail);
        tokenArchive.setCreated(new Date());
        tokenArchive.setExpired(expired);

        return tokenRepository.save(tokenArchive);
    }

    private Date setExpirationDate() {
        Instant now = Instant.now();
        Instant newInstant = now.plus(StaticResources.JWT_EXPIRATION_MS, ChronoUnit.MILLIS);
        return Date.from(newInstant);
    }

    public TokenArchive findTokenIfExist(String token) {

        var _token = tokenRepository.findByToken(token);

        if (_token != null) {
            return _token;
        }
        return null;
    }

    public Boolean delete(String token) {

        tokenRepository.deleteByToken(token);
        return true;
    }

    public UserModel findUserByToken(String token) {
        String userName = tokenProvider.getUsernameFromToken(token);

        var userOptional = userRepository.findByUsername(userName);
        if(userOptional.isPresent()) {

            return userRepository.findByUsername(userName).get();
        }
        return null;
    }
}
