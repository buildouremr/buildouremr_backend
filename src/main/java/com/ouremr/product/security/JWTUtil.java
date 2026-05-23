package com.ouremr.product.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET = "SECRET_KEY_123_SECRET_KEY_123_SECRET_KEY_123"; // must be >= 32 chars
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour
//    private final long EXPIRATION = 1000 * 30;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ✅ GENERATE TOKEN
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // ✅ FIXED
                .setIssuedAt(new Date()) // ✅ FIXED
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // ✅ FIXED
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ EXTRACT USERNAME
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ VALIDATE TOKEN
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ✅ PARSE CLAIMS
    private Claims getClaims(String token) {
        return Jwts.parserBuilder() // ✅ FIXED (not parser())
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}