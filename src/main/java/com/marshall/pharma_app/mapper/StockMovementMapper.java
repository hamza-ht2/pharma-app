package com.marshall.pharma_app.mapper;

import com.marshall.pharma_app.dto.response.movement.StockMovementResponse;
import com.marshall.pharma_app.models.StockMovement;

public class StockMovementMapper {
    public static StockMovementResponse toResponse(StockMovement stockMovement){
        if (stockMovement == null){
            return null;
        }
        return new StockMovementResponse(
                stockMovement.getId(),
                stockMovement.getProduct() != null ? stockMovement.getProduct().getId() : null,
                stockMovement.getProduct() != null ? stockMovement.getProduct().getName() : null,
                stockMovement.getBatch() != null ? stockMovement.getBatch().getId() : null,
                stockMovement.getBatch() != null ? stockMovement.getBatch().getBatchNumber() : null,
                stockMovement.getChangeNumber(), stockMovement.getReason(), stockMovement.getReferenceId(),
                stockMovement.getNote(),
                stockMovement.getCreatedByUser() != null ? stockMovement.getCreatedByUser().getId() : null,
                stockMovement.getCreatedByUser() != null ? stockMovement.getCreatedByUser().getFullName() : null,
                stockMovement.getCreatedAt()
        );
    }
}
