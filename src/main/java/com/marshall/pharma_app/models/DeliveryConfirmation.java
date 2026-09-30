package com.marshall.pharma_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "delivery_confirmations", uniqueConstraints = {
        @UniqueConstraint(name = "uk_delivery_confirmation_token", columnNames = "token")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false, unique = true)
    private Delivery delivery;
    @Column(nullable = false, length = 255)
    private String token;
    @Column(nullable = false , name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "used_at")
    private LocalDateTime usedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scanned_by_user_id")
    private User scannedByUser;
    @Column(name = "scanned_at")
    private LocalDateTime scannedAt;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column(name = "photo_url", length = 500)
    private String photoUrl;
    @Column(name = "signature_url", length = 500)
    private String signatureUrl;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(nullable = false)
    @Builder.Default
    private boolean partial = false;
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
