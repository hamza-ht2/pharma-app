package com.marshall.pharma_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_batches",uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_batch", columnNames = {"product_id", "batch_number"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false, length = 100, name = "batch_number")
    private String batchNumber;
    @Column(nullable = false, name = "expiry_date")
    private LocalDate expiryDate;
    @Column(nullable = false, name = "quantity_on_hand")
    @Builder.Default
    private int quantityOnHand = 0;
    @Column(nullable = false, name = "quantity_reserved")
    @Builder.Default
    private int quantityReserved = 0;
    @Column(nullable = false, name = "low_stock_threshold")
    @Builder.Default
    private int lowStockThreshold = 10;
    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
