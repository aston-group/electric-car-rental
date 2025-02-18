package ru.astongroup.usermanagement.components;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import ru.astongroup.usermanagement.models.UserModel;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "yourSecretKeyShouldBeLongerAndMoreComplex";
    private final long JWT_EXPIRATION_MS = 1000 * 60 * 60 * 24; // 24 hours in milliseconds

    // Метод для получения JWT из строки токена
    public String getToken(UserModel userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    // Метод для получения имени пользователя из JWT
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Общий метод для получения информации из JWT
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Метод для получения всех утверждений (claims) из JWT
    private Claims getAllClaimsFromToken(String token) {

        try {
            return Jwts.parser().setSigningKey(JWT_SECRET).build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT signature");
        }
    }

    // Метод для проверки, не истек ли JWT
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Метод для получения даты истечения срока действия из JWT
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Метод для проверки валидности JWT
    public boolean validateToken(String token, UserModel userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
