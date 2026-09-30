package com.marshall.pharma_app.mapper;

import com.marshall.pharma_app.dto.request.product.CreateProductRequest;
import com.marshall.pharma_app.dto.request.product.UpdateProductRequest;
import com.marshall.pharma_app.dto.response.product.ProductResponse;
import com.marshall.pharma_app.models.Category;
import com.marshall.pharma_app.models.Product;

public class ProductMapper {
    public static ProductResponse toResponse(Product product){
        if (product == null){
            return null;
        }
        return new ProductResponse(
                product.getId(), product.getName(), product.getDescription(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getManufacturer(), product.getDosage(), product.getForm(),
                product.getBarcode(), product.getUnitPrice(), product.isPrescriptionRequired(),
                product.isActive(), product.getImageUrl(), product.getCreatedAt(), product.getUpdatedAt()
        );
    }

    public static Product toEntity(CreateProductRequest request, Category category){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .manufacturer(request.getManufacturer())
                .dosage(request.getDosage())
                .form(request.getForm())
                .barcode(request.getBarcode())
                .unitPrice(request.getUnitPrice())
                .prescriptionRequired(request.getPrescriptionRequired() != null ? request.getPrescriptionRequired() : false)
                .active(request.getActive() != null ? request.getActive() : true)
                .imageUrl(request.getImageUrl())
                .build();
    }

    public static void updateEntity(UpdateProductRequest request, Product product, Category category){
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (category != null) product.setCategory(category);
        if (request.getManufacturer() != null) product.setManufacturer(request.getManufacturer());
        if (request.getDosage() != null) product.setDosage(request.getDosage());
        if (request.getForm() != null) product.setForm(request.getForm());
        if (request.getBarcode() != null) product.setBarcode(request.getBarcode());
        if (request.getUnitPrice() != null) product.setUnitPrice(request.getUnitPrice());
        if (request.getPrescriptionRequired() != null) product.setPrescriptionRequired(request.getPrescriptionRequired());
        if (request.getActive() != null) product.setActive(request.getActive());
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl());
    }
}