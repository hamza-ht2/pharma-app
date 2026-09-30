package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.DeliveryConfirmation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryConfirmationRepository extends JpaRepository<DeliveryConfirmation , UUID > {
    Optional<DeliveryConfirmation> findByDeliveryId(UUID deliveryId);
    Optional<DeliveryConfirmation> findByToken(String token);
    boolean existsByToken(String token);
    Page<DeliveryConfirmation> findByScannedByUserId(UUID userId, Pageable pageable);
}
