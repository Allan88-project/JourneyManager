package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.model.user.User;
import com.journeymanager.journeybackend.repository.UserRepository;
import com.journeymanager.journeybackend.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getTenant().getSubdomain(),
                user.getRole().name()
        );
    }
}
