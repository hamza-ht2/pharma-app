package com.marshall.pharma_app.dto.request.batch;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBatchRequest {
    @NotNull(message = "product id is required")
    private UUID productId;

    @NotBlank(message = "batch number is required")
    private String batchNumber;

    @NotNull(message = "expiry date is required")
    @Future(message = "expiry date must be in the future")
    private LocalDate expiryDate;

    @NotNull(message = "quantity on hand is required")
    private Integer quantityOnHand;

    private Integer lowStockThreshold;

    private Boolean active;
}