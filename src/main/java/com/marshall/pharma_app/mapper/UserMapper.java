package com.marshall.pharma_app.mapper;

import com.marshall.pharma_app.dto.request.user.RegisterUserRequest;
import com.marshall.pharma_app.dto.request.user.UpdateProfileRequest;
import com.marshall.pharma_app.dto.response.auth.UserProfileAdminResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;
import com.marshall.pharma_app.models.User;

public class UserMapper {
    public static UserProfileResponse toResponse(User user){
        return new UserProfileResponse(
                user.getId(), user.getFullName(), user.getEmail(), user.getPhone(),
                user.getRole(), user.getAvatarUrl(), user.getCreatedAt()
        );
    }
    public static UserProfileAdminResponse toAdminResponse(User user){
        return new UserProfileAdminResponse(
                user.getId(), user.getFullName(), user.getEmail(), user.getPhone(),
                user.getRole(), user.getAvatarUrl(),user.isActive() , user.getLastLoginAt(), user.getPasswordChangedAt(),
                user.getCreatedAt(), user.getUpdatedAt()
        );
    }

    public static User toEntity(RegisterUserRequest request){
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail().toLowerCase())
                .phone(request.getPhone())
                .password(request.getPassword())
                .role(request.getRole())
                .avatarUrl(request.getAvatarUrl())
                .build();
    }

    public static void updateEntity(User user , UpdateProfileRequest request){
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
    }
}
