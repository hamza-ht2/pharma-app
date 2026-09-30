package com.marshall.pharma_app.dto.request.user;

import com.marshall.pharma_app.models.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {
    @NotBlank(message = "full name is required")
    private String fullName;
    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;
    @NotBlank(message = "email is required")
    @Email(message = "email format not valid")
    private String email;
    @NotBlank(message = "password is required")
    @Size(message = "password must be at least 8 characters" ,min = 8, max = 16)
    private String password;
    @NotNull(message = "role is required")
    private Role role;
    private String avatarUrl;
}
