package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.request.category.CategoryRequest;
import com.marshall.pharma_app.dto.request.category.UpdateCategoryRequest;
import com.marshall.pharma_app.dto.response.category.CategoryResponse;
import com.marshall.pharma_app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id, @Valid @RequestBody UpdateCategoryRequest request){
        CategoryResponse updated = categoryService.updateCategory(id,request);
        return ResponseEntity.ok(updated);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAll(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }
    @GetMapping("/search")
    public ResponseEntity<Page<CategoryResponse>> searchByName(@RequestParam String name, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(categoryService.getCategoriesByName(name, pageable));
    }
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<Page<CategoryResponse>> getByParentId(@PathVariable UUID parentId, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(categoryService.getCategoriesByParentId(parentId, pageable));
    }
    @GetMapping("/active")
    public ResponseEntity<Page<CategoryResponse>> getActiveCategories(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(categoryService.getActive(pageable));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
