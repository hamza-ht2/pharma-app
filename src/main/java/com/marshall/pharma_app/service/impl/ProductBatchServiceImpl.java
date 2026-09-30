package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.request.batch.CreateBatchRequest;
import com.marshall.pharma_app.dto.request.batch.UpdateBatchRequest;
import com.marshall.pharma_app.dto.response.batch.ProductBatchResponse;
import com.marshall.pharma_app.exceptions.BusinessRuleException;
import com.marshall.pharma_app.exceptions.DuplicateResourceException;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.ProductBatchMapper;
import com.marshall.pharma_app.models.Product;
import com.marshall.pharma_app.models.ProductBatch;
import com.marshall.pharma_app.repositories.ProductBatchRepository;
import com.marshall.pharma_app.repositories.ProductRepository;
import com.marshall.pharma_app.service.ProductBatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ProductBatchServiceImpl implements ProductBatchService {
    private final ProductBatchRepository batchRepository;
    private final ProductRepository productRepository;

    public ProductBatchServiceImpl(ProductBatchRepository batchRepository, ProductRepository productRepository) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductBatchResponse createBatch(CreateBatchRequest request) {
        if (batchRepository.existsByProductIdAndBatchNumber(request.getProductId(), request.getBatchNumber())){
            throw new DuplicateResourceException("batch with this number already exist for this product");
        }
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new ResourceNotFoundException("product not found with id :"+request.getProductId()));
        ProductBatch productBatch = ProductBatchMapper.toEntity(request,product);
        ProductBatch saved = batchRepository.save(productBatch);
        return ProductBatchMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductBatchResponse updateBatch(UUID batchId, UpdateBatchRequest request) {
        ProductBatch productBatch = batchRepository.findById(batchId).orElseThrow(()-> new ResourceNotFoundException("product batch not found with id :"+batchId));
        ProductBatchMapper.updateEntity(request,productBatch);
        ProductBatch saved = batchRepository.save(productBatch);
        return ProductBatchMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductBatchResponse getBatchById(UUID id) {
        ProductBatch productBatch = batchRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("product batch not found with id :"+id));
        return ProductBatchMapper.toResponse(productBatch);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductBatchResponse getByProductIdAndBatchNumber(UUID productId, String batchNumber) {
        ProductBatch productBatch = batchRepository.findByProductIdAndBatchNumber(productId, batchNumber).orElseThrow(()-> new ResourceNotFoundException("batch not found with number :"+batchNumber+" for product :"+productId));
        return ProductBatchMapper.toResponse(productBatch);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBatchResponse> getAll(Pageable pageable) {
        Page<ProductBatch> productBatches = batchRepository.findAll(pageable);
        return productBatches.map(ProductBatchMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBatchResponse> getByProductId(UUID productId, Pageable pageable) {
        Page<ProductBatch> batches = batchRepository.findByProductId(productId,pageable);
        return batches.map(ProductBatchMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBatchResponse> getAvailableBatchesByProductId(UUID productId, Pageable pageable) {
        Page<ProductBatch> batches = batchRepository.findAvailableForFefo(productId,pageable);
        return batches.map(ProductBatchMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBatchResponse> getExpiringBatches(int days, Pageable pageable) {
        LocalDate maxDate = LocalDate.now().plusDays(days);
        Page<ProductBatch> batches = batchRepository.findExpiringBatches(maxDate,pageable);
        return batches.map(ProductBatchMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductBatchResponse> getLowStockBatches(Pageable pageable) {
        Page<ProductBatch> batches = batchRepository.findLowStockBatches(pageable);
        return batches.map(ProductBatchMapper::toResponse);
    }

    @Override
    @Transactional
    public ProductBatchResponse deactivateBatch(UUID id) {
        ProductBatch batch = batchRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("batch not found with id :"+id));
        if (!batch.isActive()){
            throw new BusinessRuleException("batch already inactive");
        }
        batch.setActive(false);
        ProductBatch saved = batchRepository.save(batch);
        return ProductBatchMapper.toResponse(saved);
    }

}
