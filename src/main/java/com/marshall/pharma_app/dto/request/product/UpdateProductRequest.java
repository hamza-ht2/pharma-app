package com.marshall.pharma_app.dto.request.product;

import jakarta.validation.constraints.DecimalMin;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private String name;
    private String description;
    private UUID categoryId;
    private String manufacturer;
    private String dosage;
    private String form;
    private String barcode;
    @DecimalMin(value = "0.0", message = "price must be positive")
    private BigDecimal unitPrice;
    private Boolean prescriptionRequired;
    private Boolean active;
    private String imageUrl;
}
