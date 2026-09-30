package com.marshall.pharma_app.dto.request.pharmacy;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePharmacyRequest {
    private String name;
    private String licenseNumber;
    private String address;
    private String city;
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;
    @Email(message = "email format not valid")
    private String email;
    private UUID ownerId;
    private Boolean active;
}
