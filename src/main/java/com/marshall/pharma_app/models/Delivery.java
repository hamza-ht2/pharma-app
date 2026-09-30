package com.marshall.pharma_app.models;

import com.marshall.pharma_app.models.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deliveries", uniqueConstraints = {
        @UniqueConstraint(name = "uk_delivery_order", columnNames = "order_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @Column(nullable = false, name = "assigned_at")
    private LocalDateTime assignedAt;
    @Column(name = "picked_up_at")
    private LocalDateTime pickedUpAt;
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    @Column(length = 500, name = "failed_reason")
    private String failedReason;
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
