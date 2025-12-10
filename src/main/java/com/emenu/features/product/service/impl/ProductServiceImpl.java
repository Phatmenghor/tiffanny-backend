package com.emenu.features.product.service.impl;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.ProductRequest;
import com.emenu.features.product.dto.response.ProductResponse;
import com.emenu.features.product.mapper.ProductMapper;
import com.emenu.features.product.models.*;
import com.emenu.features.product.repository.*;
import com.emenu.features.product.service.ProductService;
import com.emenu.features.product.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating new product: {}", request.getName());
        
        // Validate category
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
        
        // Validate subcategory if provided
        SubCategory subCategory = null;
        if (request.getSubCategoryId() != null) {
            subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(request.getSubCategoryId())
                    .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + request.getSubCategoryId()));
        }
        
        // Create product
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product.setSubCategory(subCategory);
        
        // Add attributes
        if (request.getAttributes() != null && !request.getAttributes().isEmpty()) {
            request.getAttributes().forEach(attrReq -> {
                ProductAttribute attribute = new ProductAttribute();
                attribute.setAttributeName(attrReq.getAttributeName());
                attribute.setAttributeValue(attrReq.getAttributeValue());
                attribute.setProduct(product);
                product.getAttributes().add(attribute);
            });
        }
        
        // Add variants
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            request.getVariants().forEach(varReq -> {
                ProductVariant variant = new ProductVariant();
                variant.setName(varReq.getName());
                variant.setPrice(varReq.getPrice());
                variant.setStock(varReq.getStock());
                variant.setDiscount(varReq.getDiscount());
                variant.setDiscountType(varReq.getDiscountType());
                variant.setDiscountStartDate(varReq.getDiscountStartDate());
                variant.setDiscountEndDate(varReq.getDiscountEndDate());
                variant.setImageCover(varReq.getImageCover());
                variant.setProduct(product);
                product.getVariants().add(variant);
            });
        }
        
        Product savedProduct = productRepository.save(product);
        
        log.info("Product created with ID: {}", savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        log.info("Updating product with ID: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        // Update category if changed
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
            product.setCategory(category);
        }
        
        // Update subcategory if changed
        if (request.getSubCategoryId() != null) {
            if (product.getSubCategory() == null || !product.getSubCategory().getId().equals(request.getSubCategoryId())) {
                SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(request.getSubCategoryId())
                        .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + request.getSubCategoryId()));
                product.setSubCategory(subCategory);
            }
        } else {
            product.setSubCategory(null);
        }
        
        // Update basic fields
        productMapper.updateEntity(request, product);
        
        // Update attributes - clear and re-add
        product.getAttributes().clear();
        if (request.getAttributes() != null && !request.getAttributes().isEmpty()) {
            request.getAttributes().forEach(attrReq -> {
                ProductAttribute attribute = new ProductAttribute();
                attribute.setAttributeName(attrReq.getAttributeName());
                attribute.setAttributeValue(attrReq.getAttributeValue());
                attribute.setProduct(product);
                product.getAttributes().add(attribute);
            });
        }
        
        // Update variants - clear and re-add
        product.getVariants().clear();
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            request.getVariants().forEach(varReq -> {
                ProductVariant variant = new ProductVariant();
                variant.setName(varReq.getName());
                variant.setPrice(varReq.getPrice());
                variant.setStock(varReq.getStock());
                variant.setDiscount(varReq.getDiscount());
                variant.setDiscountType(varReq.getDiscountType());
                variant.setDiscountStartDate(varReq.getDiscountStartDate());
                variant.setDiscountEndDate(varReq.getDiscountEndDate());
                variant.setImageCover(varReq.getImageCover());
                variant.setProduct(product);
                product.getVariants().add(variant);
            });
        }
        
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product updated: {}", id);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Deleting product with ID: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        product.softDelete();
        productRepository.save(product);
        
        log.info("Product soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        log.info("Fetching product with ID: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all active products");
        
        // Use specification to find all active products
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, null, null, null, null, null, null, null, null, null, null)
        ).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(UUID categoryId) {
        log.info("Fetching products for category ID: {}", categoryId);
        
        // Use specification to filter by category
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, null, categoryId, null, null, null, null, null, null, null, null)
        ).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsBySubCategory(UUID subCategoryId) {
        log.info("Fetching products for subcategory ID: {}", subCategoryId);
        
        // Use specification to filter by subcategory
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, null, null, subCategoryId, null, null, null, null, null, null, null)
        ).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByStatus(Status status) {
        log.info("Fetching products with status: {}", status);
        
        // Use specification to filter by status
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, status, null, null, null, null, null, null, null, null, null)
        ).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void incrementProductView(UUID id) {
        log.info("Incrementing product view for ID: {}", id);
        
        productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        product.setProductView(product.getProductView() + 1);
        productRepository.save(product);
        
        log.info("Product view incremented for ID: {}", id);
    }
}

