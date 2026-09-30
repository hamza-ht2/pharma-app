package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.request.auth.LoginRequest;
import com.marshall.pharma_app.dto.response.auth.LoginResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    UserProfileResponse me(String email);
}
