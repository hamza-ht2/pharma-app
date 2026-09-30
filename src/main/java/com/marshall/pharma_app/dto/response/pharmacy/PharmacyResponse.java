package com.marshall.pharma_app.dto.response.pharmacy;

import java.time.LocalDateTime;
import java.util.UUID;

public record PharmacyResponse(
        UUID id, String name, String licenseNumber , String address,
        String city , String phone , String email, UUID ownerId,
        String ownerFullName, boolean active , LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
