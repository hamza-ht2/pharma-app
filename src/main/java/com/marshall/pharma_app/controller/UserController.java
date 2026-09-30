package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.request.user.RegisterUserRequest;
import com.marshall.pharma_app.dto.request.user.UpdateEmailRequest;
import com.marshall.pharma_app.dto.request.user.UpdatePasswordRequest;
import com.marshall.pharma_app.dto.request.user.UpdateProfileRequest;
import com.marshall.pharma_app.dto.response.auth.UserProfileAdminResponse;
import com.marshall.pharma_app.dto.response.auth.UserProfileResponse;
import com.marshall.pharma_app.models.enums.Role;
import com.marshall.pharma_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileAdminResponse> createUser(@Valid @RequestBody RegisterUserRequest registerUserRequest){
        UserProfileAdminResponse created = userService.createUser(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileAdminResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserProfileAdminResponse>> getAllUsers(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserProfileAdminResponse>> getByRole(@PathVariable Role role, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(userService.getByRole(role, pageable));
    }
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateUser(@PathVariable UUID id){
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateUser(@PathVariable UUID id){
        userService.activateUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> myProfile(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.getMyProfile(userDetails.getUsername()));
    }
    @PatchMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.updateProfile(request, userDetails.getUsername()));
    }
    @PatchMapping("/profile/change-password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal UserDetails userDetails){
        userService.updatePassword(request, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/profile/update-email")
    public ResponseEntity<UserProfileResponse> updateEmail(@Valid @RequestBody UpdateEmailRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.updateEmail(request, userDetails.getUsername()));
    }


}
