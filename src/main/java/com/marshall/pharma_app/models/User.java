package com.marshall.pharma_app.models;

import com.marshall.pharma_app.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_user_phone", columnNames = "phone")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, name = "full_name", length = 150)
    private String fullName;
    @Column(nullable = false, length = 150)
    private String email;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(length = 500, name = "avatar_url ")
    private String avatarUrl;
    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;
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
