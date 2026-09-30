package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.Order;
import com.marshall.pharma_app.models.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order , UUID> {
    Page<Order> findByPharmacyId(UUID pharmacyId, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findByPlacedByUserId(UUID userId, Pageable pageable);
    Page<Order> findByPharmacyIdAndStatus(UUID pharmacyId, OrderStatus status, Pageable pageable);
    int countByStatus(OrderStatus status);
    int countByPharmacyIdAndStatus(UUID pharmacyId, OrderStatus status);
}
