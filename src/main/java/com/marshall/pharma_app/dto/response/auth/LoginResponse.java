package com.marshall.pharma_app.dto.response.auth;

import com.marshall.pharma_app.models.enums.Role;

import java.util.UUID;

public record LoginResponse(
        String token, UUID userId,  String email,String fullName, Role role
) {
}
