package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.request.auth.LoginRequest;
import com.marshall.pharma_app.dto.response.auth.LoginResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.UserMapper;
import com.marshall.pharma_app.models.User;
import com.marshall.pharma_app.repositories.UserRepository;
import com.marshall.pharma_app.security.JwtUtils;
import com.marshall.pharma_app.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().toLowerCase();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email, request.getPassword()
                )
        );
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found with email :"+email));
        String token = jwtUtils.generateToken(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        return new LoginResponse(token, user.getId() , email, user.getFullName(), user.getRole());
    }

    @Override
    public UserProfileResponse me(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found with email:"+email));
        return UserMapper.toResponse(user);
    }
}
