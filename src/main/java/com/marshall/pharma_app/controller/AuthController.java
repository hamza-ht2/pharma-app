package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.request.auth.LoginRequest;
import com.marshall.pharma_app.dto.response.auth.LoginResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;
import com.marshall.pharma_app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> myProfile(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(authService.me(userDetails.getUsername()));
    }
}
