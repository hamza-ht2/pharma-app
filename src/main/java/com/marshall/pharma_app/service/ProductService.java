package com.marshall.pharma_app.service;

import com.marshall.pharma_app.dto.request.product.CreateProductRequest;
import com.marshall.pharma_app.dto.request.product.UpdateProductRequest;
import com.marshall.pharma_app.dto.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse updateProduct(UUID productId, UpdateProductRequest request);
    ProductResponse getProductById(UUID id);
    ProductResponse getProductByBarcode(String barcode);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    Page<ProductResponse> getActiveProducts(Pageable pageable);
    Page<ProductResponse> getProductsByCategoryId(UUID categoryId, Pageable pageable);
    Page<ProductResponse> getActiveByCategoryId(UUID categoryId, Pageable pageable);
    Page<ProductResponse> getProductsByName(String name,Pageable pageable);
    Page<ProductResponse> getByPrescriptionRequired(Pageable pageable);
    void deleteProduct(UUID id);
    ProductResponse deactivateProduct(UUID id);
}
