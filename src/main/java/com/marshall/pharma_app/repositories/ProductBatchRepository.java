package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.ProductBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ProductBatchRepository extends JpaRepository<ProductBatch, UUID> {
    Page<ProductBatch> findByProductId(UUID productId, Pageable pageable);
    Optional<ProductBatch> findByProductIdAndBatchNumber(UUID productId, String batchNumber);
    boolean existsByProductIdAndBatchNumber(UUID productId, String batchNumber);
    @Query("""
    SELECT b FROM ProductBatch b
    WHERE b.product.id = :productId
      AND b.active = true
      AND (b.quantityOnHand - b.quantityReserved) > 0
    ORDER BY b.expiryDate ASC
""")
    Page<ProductBatch> findAvailableForFefo(@Param("productId") UUID productId, Pageable pageable);

    @Query("""
    SELECT b FROM ProductBatch b
    WHERE b.active = true
      AND b.expiryDate <= :maxDate
    ORDER BY b.expiryDate ASC
""")
    Page<ProductBatch> findExpiringBatches(@Param("maxDate") LocalDate maxDate, Pageable pageable);

    @Query("""
    SELECT b FROM ProductBatch b
    WHERE b.active = true
      AND b.quantityOnHand <= b.lowStockThreshold
""")
    Page<ProductBatch> findLowStockBatches(Pageable pageable);
}