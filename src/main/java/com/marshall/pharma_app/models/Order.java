package com.marshall.pharma_app.models;

import com.marshall.pharma_app.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="pharmacy_id", nullable = false)
    private Pharmacy pharmacy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placed_by_user_id", nullable = false)
    private User placedByUser;
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(length = 500, name = "rejection_reason")
    private String rejectionReason;
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
