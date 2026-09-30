package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PharmacyRepository extends JpaRepository<Pharmacy , UUID> {
    Optional<Pharmacy> findByLicenseNumber(String licenseNumber);
    Page<Pharmacy> findByNameContainingIgnoreCase(String name,Pageable pageable);
    Page<Pharmacy> findByOwnerId(UUID ownerId, Pageable pageable);
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    Page<Pharmacy> findByActiveTrue(Pageable pageable);

}
