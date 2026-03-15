package com.journeymanager.journeybackend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String header = httpRequest.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                if (token.contains(".")) {

                    Claims claims = jwtUtil.validateToken(token);

                    String email = claims.getSubject();
                    String tenant = claims.get("tenant", String.class);
                    String role = claims.get("role", String.class);

                    httpRequest.setAttribute("email", email);
                    httpRequest.setAttribute("tenant", tenant);
                    httpRequest.setAttribute("role", role);
                }

            } catch (Exception e) {
                System.out.println("Invalid JWT token");
            }
        }

        chain.doFilter(request, response);
    }
}