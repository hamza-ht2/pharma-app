package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.response.movement.StockMovementResponse;
import com.marshall.pharma_app.models.enums.StockMovementReason;
import com.marshall.pharma_app.service.StockMovementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/stock-movements")
@PreAuthorize("hasRole('ADMIN')")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(stockMovementService.getById(id));
    }
    @GetMapping
    public ResponseEntity<Page<StockMovementResponse>> getAll(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(stockMovementService.getAll(pageable));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<StockMovementResponse>> getByProductId(@PathVariable UUID productId, @PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(stockMovementService.getByProductId(productId, pageable));
    }
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<Page<StockMovementResponse>> getByBatchId(@PathVariable UUID batchId, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(stockMovementService.getByBatchId(batchId, pageable));
    }
    @GetMapping("/reference/{referenceId}")
    public ResponseEntity<Page<StockMovementResponse>> getByReference(@PathVariable UUID referenceId, @PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(stockMovementService.getByReferenceId(referenceId, pageable));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<StockMovementResponse>> getByUserId(@PathVariable UUID userId, @PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(stockMovementService.getByCreatedByUserId(userId, pageable));
    }
    @GetMapping("/reason/{reason}")
    public ResponseEntity<Page<StockMovementResponse>> getByReason(@PathVariable StockMovementReason reason, @PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(stockMovementService.getByReason(reason, pageable));
    }
}
