package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.request.batch.CreateBatchRequest;
import com.marshall.pharma_app.dto.request.batch.UpdateBatchRequest;
import com.marshall.pharma_app.dto.response.batch.ProductBatchResponse;
import com.marshall.pharma_app.service.ProductBatchService;
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
@RequestMapping("/api/product-batches")
public class ProductBatchController {
    private final ProductBatchService batchService;

    public ProductBatchController(ProductBatchService batchService) {
        this.batchService = batchService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductBatchResponse> createBatch(@Valid @RequestBody CreateBatchRequest request){
        ProductBatchResponse created = batchService.createBatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductBatchResponse> updateBatch(@PathVariable UUID id, @Valid @RequestBody UpdateBatchRequest request){
        ProductBatchResponse updated = batchService.updateBatch(id,request);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductBatchResponse> deactivateBatch(@PathVariable UUID id){
        return ResponseEntity.ok(batchService.deactivateBatch(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<ProductBatchResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(batchService.getBatchById(id));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductBatchResponse>> getAll(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(batchService.getAll(pageable));
    }
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductBatchResponse>> getByProductId(@PathVariable UUID productId, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(batchService.getByProductId(productId, pageable));
    }
    @GetMapping("/product/{productId}/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<Page<ProductBatchResponse>> getAvailable(@PathVariable UUID productId, @PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(batchService.getAvailableBatchesByProductId(productId, pageable));
    }
    @GetMapping("/product/{productId}/batch/{batchNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<ProductBatchResponse> getByProductAndNumber(@PathVariable UUID productId, @PathVariable String batchNumber){
        return ResponseEntity.ok(batchService.getByProductIdAndBatchNumber(productId, batchNumber));
    }
    @GetMapping("/expiring")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductBatchResponse>> getExpiring(@RequestParam(defaultValue = "30") int days, @PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(batchService.getExpiringBatches(days, pageable));
    }
    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProductBatchResponse>> getLowStock(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(batchService.getLowStockBatches(pageable));
    }



}
