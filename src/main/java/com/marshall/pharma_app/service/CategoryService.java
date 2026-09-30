package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.request.category.CategoryRequest;
import com.marshall.pharma_app.dto.request.category.UpdateCategoryRequest;
import com.marshall.pharma_app.dto.response.category.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request);
    CategoryResponse getCategoryById(UUID categoryId);
    void deleteCategory(UUID categoryId);
    Page<CategoryResponse> getAllCategories(Pageable pageable);
    Page<CategoryResponse> getCategoriesByName(String name, Pageable pageable);
    Page<CategoryResponse> getCategoriesByParentId(UUID parentId, Pageable pageable);
    Page<CategoryResponse> getActive(Pageable pageable);
}