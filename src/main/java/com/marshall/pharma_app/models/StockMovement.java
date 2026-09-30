package com.marshall.pharma_app.models;

import com.marshall.pharma_app.models.enums.StockMovementReason;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_batch_id")
    private ProductBatch batch;
    @Column(nullable = false, name = "change_number")
    private int changeNumber;
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private StockMovementReason reason;
    @Column(name = "reference_id")
    private UUID referenceId;
    @Column(length = 500)
    private String note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdByUser;
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt= LocalDateTime.now();
    }

}
