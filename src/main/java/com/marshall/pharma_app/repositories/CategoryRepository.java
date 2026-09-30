package com.marshall.pharma_app.repositories;

import com.marshall.pharma_app.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category , UUID> {
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Category> findByParentCategoryId(UUID parentCategoryId, Pageable pageable);
    Page<Category> findByActiveTrueOrderByDisplayOrderAsc(Pageable pageable);
    boolean existsByName(String name);
}
