package com.marshall.pharma_app.dto.response.category;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id, String name , String description, String nameFr, String nameAr,
        UUID parentCategoryId, String parentCategoryName, int displayOrder, boolean active,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
}
