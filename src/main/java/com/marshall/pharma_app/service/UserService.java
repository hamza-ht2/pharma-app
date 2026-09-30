package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.request.user.RegisterUserRequest;
import com.marshall.pharma_app.dto.request.user.UpdateEmailRequest;
import com.marshall.pharma_app.dto.request.user.UpdatePasswordRequest;
import com.marshall.pharma_app.dto.request.user.UpdateProfileRequest;
import com.marshall.pharma_app.dto.response.auth.UserProfileAdminResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;
import com.marshall.pharma_app.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserProfileAdminResponse createUser(RegisterUserRequest registerUserRequest);
    UserProfileAdminResponse getUserById(UUID id);
    Page<UserProfileAdminResponse> getAllUsers(Pageable pageable);
    Page<UserProfileAdminResponse> getByRole(Role role, Pageable pageable);
    void deactivateUser(UUID id);
    void activateUser(UUID id);
    UserProfileResponse getMyProfile(String userEmail);
    UserProfileResponse updateProfile(UpdateProfileRequest request, String email);
    void updatePassword(UpdatePasswordRequest request, String email);
    UserProfileResponse updateEmail(UpdateEmailRequest request, String email);
}
