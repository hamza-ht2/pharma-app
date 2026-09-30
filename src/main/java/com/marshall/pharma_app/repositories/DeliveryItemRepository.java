package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.DeliveryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem , UUID> {
    List<DeliveryItem> findByDeliveryId(UUID deliveryId);
    List<DeliveryItem> findByOrderItemId(UUID orderItemId);
    Page<DeliveryItem> findByBatchId(UUID batchId, Pageable pageable);
}
