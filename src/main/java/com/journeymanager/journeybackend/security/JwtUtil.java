package com.journeymanager.journeybackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key =
            Keys.hmacShaKeyFor(
                    "verysecretkeyverysecretkey123456789".getBytes()
            );

    /**
     * Generate JWT token
     */
    public String generateToken(String email,
                                String tenant,
                                String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("tenant", tenant)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
                )
                .signWith(key)
                .compact();
    }

    /**
     * Parse and validate token
     */
    public Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extract username (email)
     */
    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    /**
     * Extract tenant code
     */
    public String extractTenant(String token) {
        return validateToken(token).get("tenant", String.class);
    }

    /**
     * Extract role
     */
    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }

    /**
     * Check token validity
     */
    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}