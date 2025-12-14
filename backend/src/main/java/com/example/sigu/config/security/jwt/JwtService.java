package com.example.sigu.config.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    private final String secretKey = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private final Duration expiration = Duration.ofMinutes(15);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(expiration)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            return !extractExpiration(token).before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
