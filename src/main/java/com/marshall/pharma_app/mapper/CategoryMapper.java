package com.marshall.pharma_app.mapper;

import com.marshall.pharma_app.dto.request.category.CategoryRequest;
import com.marshall.pharma_app.dto.request.category.UpdateCategoryRequest;
import com.marshall.pharma_app.dto.response.category.CategoryResponse;
import com.marshall.pharma_app.models.Category;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getNameFr(),
                category.getNameAr(),
                category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                category.getParentCategory() != null ? category.getParentCategory().getName() : null,
                category.getDisplayOrder(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public static Category toEntity(CategoryRequest request, Category parentCategory) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .nameFr(request.getNameFr())
                .nameAr(request.getNameAr())
                .parentCategory(parentCategory)
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public static void updateEntity(UpdateCategoryRequest request, Category parentCategory, Category category) {
        if (request.getName() != null && !request.getName().isBlank()) category.setName(request.getName());
        if (request.getDescription() != null) category.setDescription(request.getDescription());
        if (request.getNameAr() != null && !request.getNameAr().isBlank()) category.setNameAr(request.getNameAr());
        if (request.getNameFr() != null && !request.getNameFr().isBlank()) category.setNameFr(request.getNameFr());
        if (request.getDisplayOrder() != null) category.setDisplayOrder(request.getDisplayOrder());
        if (request.getActive() != null) category.setActive(request.getActive());
        if (parentCategory != null) category.setParentCategory(parentCategory);
    }
}