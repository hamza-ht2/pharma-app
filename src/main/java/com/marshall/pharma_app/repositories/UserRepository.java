package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.User;
import com.marshall.pharma_app.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    Page<User> findUsersByRole(Role role, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
