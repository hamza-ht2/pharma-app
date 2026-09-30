package com.marshall.pharma_app.dto.request.auth;

import com.marshall.pharma_app.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "email is required")
    @Email(message = "email format not valid")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
}
