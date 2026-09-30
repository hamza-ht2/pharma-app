package com.marshall.pharma_app.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmailRequest {
    @NotBlank(message = "email is required")
    @Email(message = "email format not valid")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
}
