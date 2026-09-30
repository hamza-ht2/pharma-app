package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.response.movement.StockMovementResponse;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.StockMovementMapper;
import com.marshall.pharma_app.models.Product;
import com.marshall.pharma_app.models.ProductBatch;
import com.marshall.pharma_app.models.StockMovement;
import com.marshall.pharma_app.models.User;
import com.marshall.pharma_app.models.enums.StockMovementReason;
import com.marshall.pharma_app.repositories.ProductBatchRepository;
import com.marshall.pharma_app.repositories.ProductRepository;
import com.marshall.pharma_app.repositories.StockMovementRepository;
import com.marshall.pharma_app.repositories.UserRepository;
import com.marshall.pharma_app.service.StockMovementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class StockMovementServiceImpl implements StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final ProductBatchRepository batchRepository;
    private final UserRepository userRepository;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository, ProductRepository productRepository, ProductBatchRepository batchRepository, UserRepository userRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public StockMovementResponse getById(UUID id) {
        StockMovement stockMovement = stockMovementRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("stock movement not found with id :"+id));
        return StockMovementMapper.toResponse(stockMovement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getAll(Pageable pageable) {
        return stockMovementRepository.findAll(pageable).map(StockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getByProductId(UUID productId, Pageable pageable) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product not found with id :"+productId));
        Page<StockMovement> movements = stockMovementRepository.findByProductId(product.getId(),pageable);
        return movements.map(StockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getByBatchId(UUID batchId, Pageable pageable) {
        ProductBatch productBatch = batchRepository.findById(batchId).orElseThrow(()-> new ResourceNotFoundException("batch not found with id :"+batchId));
        Page<StockMovement> movements = stockMovementRepository.findByBatchId(productBatch.getId(), pageable);
        return movements.map(StockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getByReferenceId(UUID referenceId, Pageable pageable) {
        Page<StockMovement> movements = stockMovementRepository.findByReferenceId(referenceId,pageable);
        return movements.map(StockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getByCreatedByUserId(UUID createdByUserId, Pageable pageable) {
        User user = userRepository.findById(createdByUserId).orElseThrow(()-> new ResourceNotFoundException("user not found with id :"+createdByUserId));
        Page<StockMovement> movements = stockMovementRepository.findByCreatedByUserId(user.getId(), pageable);
        return movements.map(StockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getByReason(StockMovementReason reason, Pageable pageable) {
        return stockMovementRepository.findByReason(reason, pageable).map(StockMovementMapper::toResponse);
    }
}
