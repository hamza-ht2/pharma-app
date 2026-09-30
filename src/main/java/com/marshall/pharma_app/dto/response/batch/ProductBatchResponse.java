package com.marshall.pharma_app.dto.response.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductBatchResponse(
        UUID id,
        UUID productId, String productName, String batchNumber,
        LocalDate expiryDate, int quantityOnHand, int quantityReserved,
        int lowStockThreshold, boolean active , LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
