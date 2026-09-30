package com.marshall.pharma_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_barcode", columnNames = "barcode")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 200)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(length = 150)
    private String manufacturer;
    @Column(length = 100)
    private String dosage;
    @Column(length = 100)
    private String form;
    @Column(length = 100)
    private String barcode;
    @Column(nullable = false, precision = 12, scale = 2, name = "unit_price")
    private BigDecimal unitPrice;
    @Column(nullable = false, name = "prescription_required")
    @Builder.Default
    private boolean prescriptionRequired= false;
    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
    @Column(length = 500, name = "image_url")
    private String imageUrl;
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
