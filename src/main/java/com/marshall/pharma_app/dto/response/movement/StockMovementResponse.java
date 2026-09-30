package com.marshall.pharma_app.dto.response.movement;

import com.marshall.pharma_app.models.enums.StockMovementReason;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockMovementResponse(
        UUID id,
        UUID productId, String productName,
        UUID batchId, String batchNumber,
        int changeNumber, StockMovementReason reason,
        UUID referenceId, String note,UUID createdByUserId,
        String createdByUserFullName, LocalDateTime createdAt
        ) {
}
