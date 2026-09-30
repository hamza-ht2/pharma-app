package com.marshall.pharma_app.service.impl;

import com.marshall.pharma_app.dto.request.product.CreateProductRequest;
import com.marshall.pharma_app.dto.request.product.UpdateProductRequest;
import com.marshall.pharma_app.dto.response.product.ProductResponse;
import com.marshall.pharma_app.exceptions.BusinessRuleException;
import com.marshall.pharma_app.exceptions.DuplicateResourceException;
import com.marshall.pharma_app.exceptions.ResourceNotFoundException;
import com.marshall.pharma_app.mapper.ProductMapper;
import com.marshall.pharma_app.models.Category;
import com.marshall.pharma_app.models.Product;
import com.marshall.pharma_app.repositories.CategoryRepository;
import com.marshall.pharma_app.repositories.ProductRepository;
import com.marshall.pharma_app.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (request.getBarcode() != null && productRepository.existsByBarcode(request.getBarcode())) {
            throw new DuplicateResourceException("barcode already in use");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category not found with id :" + request.getCategoryId()));

        Product product = ProductMapper.toEntity(request, category);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id :" + productId));

        if (request.getBarcode() != null
                && !request.getBarcode().equals(product.getBarcode())
                && productRepository.existsByBarcode(request.getBarcode())) {
            throw new DuplicateResourceException("barcode already in use");
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("category not found with id :" + request.getCategoryId()));
        }

        ProductMapper.updateEntity(request, product, category);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id :" + id));
        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with barcode :" + barcode));
        return ProductMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getActiveProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable).map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategoryId(UUID categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable).map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getActiveByCategoryId(UUID categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable).map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable).map(ProductMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getByPrescriptionRequired(Pageable pageable) {
        return productRepository.findByActiveTrueAndPrescriptionRequiredTrue(pageable).map(ProductMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id :" + id));
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductResponse deactivateProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id :" + id));
        if (!product.isActive()) {
            throw new BusinessRuleException("product already inactive");
        }
        product.setActive(false);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }
}