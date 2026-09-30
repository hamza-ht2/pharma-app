package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.Delivery;
import com.marshall.pharma_app.models.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    Optional<Delivery> findByOrderId(UUID orderId);
    Page<Delivery> findByDriverId(UUID driverId, Pageable pageable);
    Page<Delivery> findByStatus(DeliveryStatus status, Pageable pageable);
    Page<Delivery> findByDriverIdAndStatus(UUID driverId, DeliveryStatus status, Pageable pageable);
    int countByDriverIdAndStatus(UUID driverId, DeliveryStatus status);
}
