package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.ProductRequest;
import com.emenu.features.product.dto.response.ProductResponse;
import com.emenu.features.product.service.ProductService;
import com.emenu.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Creating product: {}", request.getName());
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request) {
        log.info("Updating product: {}", id);
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id) {
        log.info("Deleting product: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable UUID id) {
        log.info("Fetching product: {}", id);
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        log.info("Fetching all products");
        List<ProductResponse> responses = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", responses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategory(@PathVariable UUID categoryId) {
        log.info("Fetching products by category: {}", categoryId);
        List<ProductResponse> responses = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", responses));
    }

    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsBySubCategory(@PathVariable UUID subCategoryId) {
        log.info("Fetching products by subcategory: {}", subCategoryId);
        List<ProductResponse> responses = productService.getProductsBySubCategory(subCategoryId);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", responses));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByStatus(@PathVariable Status status) {
        log.info("Fetching products by status: {}", status);
        List<ProductResponse> responses = productService.getProductsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", responses));
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<ApiResponse<Void>> incrementProductView(@PathVariable UUID id) {
        log.info("Incrementing product view: {}", id);
        productService.incrementProductView(id);
        return ResponseEntity.ok(ApiResponse.success("Product view incremented successfully", null));
    }
}
