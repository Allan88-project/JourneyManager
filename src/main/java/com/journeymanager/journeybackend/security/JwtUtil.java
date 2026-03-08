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

    public String generateToken(String email,
                                String tenant,
                                String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("tenant", tenant)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 86400000))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
