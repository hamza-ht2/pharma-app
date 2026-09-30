package com.marshall.pharma_app.dto.request.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "name is required")
    private String name;
    private String description;
    @NotNull(message = "category is required")
    private UUID categoryId;
    private String manufacturer;
    private String dosage;
    private String form;
    private String barcode;
    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", message = "price must be positive")
    private BigDecimal unitPrice;
    @NotNull(message = "prescription is required")
    private Boolean prescriptionRequired;
    private Boolean active;
    private String imageUrl;
}
