package com.security.service;

import com.security.dto.AuthRequest;
import com.security.dto.AuthResponse;
import com.security.exception.UserLoginException;
import com.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthService(
            JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            var user = userRepository
                    .findByEmail(request.email())
                    .orElseThrow(() -> new UserLoginException("User not found with email: " + request.email()));
            var token = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            log.info("User {} logged in successfully", user.getEmail());
            return new AuthResponse(token, refreshToken);
        } catch (Exception e) {
            log.error("Failed to login user: {}", e.getMessage());
            throw new UserLoginException("Failed to login user: " + e.getMessage());
        }
    }
}
