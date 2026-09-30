package com.marshall.pharma_app.dto.response.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name, String description, UUID categoryId, String categoryName,
        String manufacturer, String dosage , String form, String barcode,
        BigDecimal unitPrice, boolean prescriptionRequired, boolean active, String imageUrl,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
}
