package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.response.movement.StockMovementResponse;
import com.marshall.pharma_app.models.enums.StockMovementReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StockMovementService {
    StockMovementResponse getById(UUID id);
    Page<StockMovementResponse> getAll(Pageable pageable);
    Page<StockMovementResponse> getByProductId(UUID productId, Pageable pageable);
    Page<StockMovementResponse> getByBatchId(UUID batchId, Pageable pageable);
    Page<StockMovementResponse> getByReferenceId(UUID referenceId, Pageable pageable);
    Page<StockMovementResponse> getByCreatedByUserId(UUID createdByUserId, Pageable pageable);
    Page<StockMovementResponse> getByReason(StockMovementReason reason, Pageable pageable);

}
