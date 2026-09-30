package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.request.batch.CreateBatchRequest;
import com.marshall.pharma_app.dto.request.batch.UpdateBatchRequest;
import com.marshall.pharma_app.dto.response.batch.ProductBatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductBatchService {
    ProductBatchResponse createBatch(CreateBatchRequest request);
    ProductBatchResponse updateBatch(UUID batchId, UpdateBatchRequest request);
    ProductBatchResponse getBatchById(UUID id);
    ProductBatchResponse getByProductIdAndBatchNumber(UUID productId, String batchNumber);
    Page<ProductBatchResponse> getAll(Pageable pageable);
    Page<ProductBatchResponse> getByProductId(UUID productId, Pageable pageable);
    Page<ProductBatchResponse> getAvailableBatchesByProductId(UUID productId, Pageable pageable);
    Page<ProductBatchResponse> getExpiringBatches(int days, Pageable pageable);
    Page<ProductBatchResponse> getLowStockBatches(Pageable pageable);
    ProductBatchResponse deactivateBatch(UUID id);
}
