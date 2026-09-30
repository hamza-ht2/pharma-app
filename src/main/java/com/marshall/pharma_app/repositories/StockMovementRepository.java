package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.StockMovement;
import com.marshall.pharma_app.models.enums.StockMovementReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
    Page<StockMovement> findByProductId(UUID productId, Pageable pageable);
    Page<StockMovement> findByBatchId(UUID batchId, Pageable pageable);
    Page<StockMovement> findByReferenceId(UUID referenceId, Pageable pageable);
    Page<StockMovement> findByCreatedByUserId(UUID userId, Pageable pageable);
    Page<StockMovement> findByReason(StockMovementReason reason, Pageable pageable);
    Page<StockMovement> findByCreatedByUserIdAndReason(UUID userId, StockMovementReason reason, Pageable pageable);

}
