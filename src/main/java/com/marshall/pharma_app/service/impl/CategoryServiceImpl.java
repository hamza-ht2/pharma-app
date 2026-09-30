package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.request.category.CategoryRequest;
import com.marshall.pharma_app.dto.request.category.UpdateCategoryRequest;
import com.marshall.pharma_app.dto.response.category.CategoryResponse;
import com.marshall.pharma_app.exceptions.BusinessRuleException;
import com.marshall.pharma_app.exceptions.DuplicateResourceException;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.CategoryMapper;
import com.marshall.pharma_app.models.Category;
import com.marshall.pharma_app.repositories.CategoryRepository;
import com.marshall.pharma_app.repositories.ProductRepository;
import com.marshall.pharma_app.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())){
            throw new DuplicateResourceException("category already exist with name :"+request.getName());
        }
        Category parent = null;
        if (request.getParentCategoryId() != null){
            parent = categoryRepository.findById(request.getParentCategoryId()).orElseThrow(()-> new ResourceNotFoundException("category not found with id :"+request.getParentCategoryId()));
        }
        Category category = CategoryMapper.toEntity(request,parent);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id:"+categoryId));
        if (request.getName() != null && !request.getName().equals(category.getName())){
            if (categoryRepository.existsByName(request.getName())){
                throw new DuplicateResourceException("category already exist with name :"+request.getName());
            }
        }
        Category parent = null;
        if (request.getParentCategoryId() != null){
            if (request.getParentCategoryId().equals(categoryId)){
                throw new IllegalArgumentException("category cannot be its own parent");
            }
            parent = categoryRepository.findById(request.getParentCategoryId()).orElseThrow(()-> new ResourceNotFoundException("category not found with id:"+request.getParentCategoryId()));
        }
        CategoryMapper.updateEntity(request,parent,category);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id:"+categoryId));
        return CategoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id:"+categoryId));
        if (!categoryRepository.findByParentCategoryId(categoryId,Pageable.unpaged()).isEmpty()){
            throw new BusinessRuleException("cannot delete a category with sub categories");
        }
        if (productRepository.existsByCategoryId(categoryId)){
            throw new BusinessRuleException("cannot delete category with products");
        }
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getCategoriesByName(String name, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        return categories.map(CategoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getCategoriesByParentId(UUID parentId, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByParentCategoryId(parentId, pageable);
        return categories.map(CategoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getActive(Pageable pageable) {
        Page<Category> activeCategories = categoryRepository.findByActiveTrueOrderByDisplayOrderAsc(pageable);
        return activeCategories.map(CategoryMapper::toResponse);
    }
}
