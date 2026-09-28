package com.swapnil.bankmanagement.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}") // this key is very important for signing the token
    private String jwtSecretKey;

    // 1 hour
    private final long expirationTime = 1000 * 60 * 60;

    //Header
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
                // converts application properties key to UTF_8
                jwtSecretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    //Payload
    public String generateToken(UserDetails userDetails) {

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + expirationTime)
                )
                // signature with secret key
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts
                .parser()
                .verifyWith(getSecretKey()) // secret key used to verify
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
