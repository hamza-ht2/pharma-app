package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByCategoryId(UUID id, Pageable pageable);
    Page<Product> findByActiveTrue(Pageable pageable);
    Page<Product> findByCategoryIdAndActiveTrue(UUID categoryId, Pageable pageable);
    boolean existsByBarcode(String barcode);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<Product> findByBarcode(String barcode);
    Page<Product> findByActiveTrueAndPrescriptionRequiredTrue(Pageable pageable);
    boolean existsByCategoryId(UUID categoryId);
}
