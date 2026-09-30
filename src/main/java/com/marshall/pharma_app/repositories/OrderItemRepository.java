package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem , UUID> {
    List<OrderItem> findByOrderId(UUID orderId);
    Page<OrderItem> findByProductId(UUID productId, Pageable pageable);
    boolean existsByOrderIdAndProductId(UUID orderId, UUID productId);
}
