package com.marshall.pharma_app.mapper;

import com.marshall.pharma_app.dto.request.batch.CreateBatchRequest;
import com.marshall.pharma_app.dto.request.batch.UpdateBatchRequest;
import com.marshall.pharma_app.dto.response.batch.ProductBatchResponse;
import com.marshall.pharma_app.models.Product;
import com.marshall.pharma_app.models.ProductBatch;

public class ProductBatchMapper {

    public static ProductBatchResponse toResponse(ProductBatch productBatch) {
        if (productBatch == null) {
            return null;
        }
        return new ProductBatchResponse(
                productBatch.getId(),
                productBatch.getProduct() != null ? productBatch.getProduct().getId() : null,
                productBatch.getProduct() != null ? productBatch.getProduct().getName() : null,
                productBatch.getBatchNumber(),
                productBatch.getExpiryDate(),
                productBatch.getQuantityOnHand(),
                productBatch.getQuantityReserved(),
                productBatch.getLowStockThreshold(),
                productBatch.isActive(),
                productBatch.getCreatedAt(),
                productBatch.getUpdatedAt()
        );
    }

    public static ProductBatch toEntity(CreateBatchRequest request, Product product) {
        return ProductBatch.builder()
                .product(product)
                .batchNumber(request.getBatchNumber())
                .expiryDate(request.getExpiryDate())
                .quantityOnHand(request.getQuantityOnHand() != null ? request.getQuantityOnHand() : 0)
                .quantityReserved(0)
                .lowStockThreshold(request.getLowStockThreshold() != null ? request.getLowStockThreshold() : 10)
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public static void updateEntity(UpdateBatchRequest request, ProductBatch productBatch) {
        if (request.getExpiryDate() != null) productBatch.setExpiryDate(request.getExpiryDate());
        if (request.getLowStockThreshold() != null) productBatch.setLowStockThreshold(request.getLowStockThreshold());
        if (request.getActive() != null) productBatch.setActive(request.getActive());
    }
}