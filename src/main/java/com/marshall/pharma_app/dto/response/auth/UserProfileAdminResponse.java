package com.marshall.pharma_app.dto.response.auth;

import com.marshall.pharma_app.models.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileAdminResponse(
        UUID id, String fullName, String email , String phone,
        Role role, String avatarUrl,boolean active ,LocalDateTime lastLoginAt,
        LocalDateTime passwordChangedAt, LocalDateTime createdAt, LocalDateTime updatedAt
) {
}
