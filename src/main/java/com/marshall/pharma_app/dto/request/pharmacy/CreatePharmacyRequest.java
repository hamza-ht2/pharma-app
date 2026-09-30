package com.marshall.pharma_app.dto.request.pharmacy;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePharmacyRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "license number is required")
    private String licenseNumber;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;
    @NotBlank(message = "email is required")
    @Email(message = "email format not valid")
    private String email;
    @NotNull(message = "owner id is required")
    private UUID ownerId;
    private Boolean active;
}
