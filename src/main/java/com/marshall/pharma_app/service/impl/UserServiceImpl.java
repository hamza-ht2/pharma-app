package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.request.user.RegisterUserRequest;
import com.marshall.pharma_app.dto.request.user.UpdateEmailRequest;
import com.marshall.pharma_app.dto.request.user.UpdatePasswordRequest;
import com.marshall.pharma_app.dto.request.user.UpdateProfileRequest;
import com.marshall.pharma_app.dto.response.auth.UserProfileAdminResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;
import com.marshall.pharma_app.exceptions.DuplicateResourceException;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.UserMapper;
import com.marshall.pharma_app.models.User;
import com.marshall.pharma_app.models.enums.Role;
import com.marshall.pharma_app.repositories.UserRepository;
import com.marshall.pharma_app.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserProfileAdminResponse createUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
            throw new DuplicateResourceException("email already in use");
        }
        if (userRepository.existsByPhone(registerUserRequest.getPhone())) {
            throw new DuplicateResourceException("phone already in use");
        }
        User user = UserMapper.toEntity(registerUserRequest);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        User saved = userRepository.save(user);
        return UserMapper.toAdminResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileAdminResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + id));
        return UserMapper.toAdminResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileAdminResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper::toAdminResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileAdminResponse> getByRole(Role role, Pageable pageable) {
        Page<User> users = userRepository.findUsersByRole(role, pageable);
        return users.map(UserMapper::toAdminResponse);
    }

    @Override
    @Transactional
    public void deactivateUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + id));
        if (!user.isActive()) {
            throw new IllegalArgumentException("it's already deactivated");
        }
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void activateUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found with id :" + id));
        if (user.isActive()) {
            throw new IllegalArgumentException("already activated");
        }
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("user not found with email :" + userEmail));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(UpdateProfileRequest request, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with email :" + email));
        UserMapper.updateEntity(user, request);
        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordRequest request, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with email :" + email));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("password incorrect");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateEmail(UpdateEmailRequest request, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found with email :" + email));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("password incorrect");
        }
        String newEmail = request.getEmail().toLowerCase();
        if (!newEmail.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(newEmail)) {
            throw new DuplicateResourceException("email already in use");
        }
        user.setEmail(newEmail);
        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);
    }


}