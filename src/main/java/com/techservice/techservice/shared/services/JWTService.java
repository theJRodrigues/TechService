package com.techservice.techservice.shared.services;
import com.techservice.techservice.modules.account.domain.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {
    private final String secret;
    private final Long expiration;
    private final SecretKey secretKey;

    public JWTService(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION}") Long expiration
    ){
        this.secret=secret;
        this.expiration=expiration;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String id, String role){
        return Jwts.builder()
                .subject(id)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public TokenPayload validateTokenAndGetClaims(String token){
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new TokenPayload(UUID.fromString(claims.getSubject()), claims.get("role", String.class));
    }
}
