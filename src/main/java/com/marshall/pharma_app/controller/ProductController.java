package com.marshall.pharma_app.controller;

import com.marshall.pharma_app.dto.request.product.CreateProductRequest;
import com.marshall.pharma_app.dto.request.product.UpdateProductRequest;
import com.marshall.pharma_app.dto.response.product.ProductResponse;
import com.marshall.pharma_app.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request){
        ProductResponse created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id, @Valid @RequestBody UpdateProductRequest request){
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/barcode/{barcode}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<ProductResponse> getByBarcode(@PathVariable String barcode){
        return ResponseEntity.ok(productService.getProductByBarcode(barcode));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductResponse>> getAll(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductResponse>> getActive(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getActiveProducts(pageable));
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductResponse>> getByCategory(@PathVariable UUID categoryId, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId, pageable));
    }

    @GetMapping("/category/{categoryId}/active")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductResponse>> getActiveByCategory(@PathVariable UUID categoryId, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getActiveByCategoryId(categoryId, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductResponse>> searchByName(@RequestParam String name, @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getProductsByName(name, pageable));
    }

    @GetMapping("/prescription")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ResponseEntity<Page<ProductResponse>> getRequiredPrescription(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(productService.getByPrescriptionRequired(pageable));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> deactivateProduct(@PathVariable UUID id){
        return ResponseEntity.ok(productService.deactivateProduct(id));
    }
}