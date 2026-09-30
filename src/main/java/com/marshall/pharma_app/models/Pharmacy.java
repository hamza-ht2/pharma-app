package com.marshall.pharma_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pharmacies", uniqueConstraints ={
        @UniqueConstraint(name = "uk_pharmacy_license_number", columnNames = "license_number"),
        @UniqueConstraint(name = "uk_pharmacy_phone", columnNames = "phone"),
        @UniqueConstraint(name = "uk_pharmacy_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, name = "license_number",length = 100)
    private String licenseNumber;
    @Column(nullable = false, length = 255)
    private String address;
    @Column(nullable = false, length = 100)
    private String city;
    @Column(nullable = false,length = 20)
    private String phone;
    @Column(nullable = false, length = 150)
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;
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
