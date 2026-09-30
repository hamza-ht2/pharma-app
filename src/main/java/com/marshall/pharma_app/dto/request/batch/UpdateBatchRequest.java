package com.marshall.pharma_app.dto.request.batch;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBatchRequest {
    @Future(message = "expiry date must be in the future")
    private LocalDate expiryDate;
    private Integer lowStockThreshold;
    private Boolean active;
}
