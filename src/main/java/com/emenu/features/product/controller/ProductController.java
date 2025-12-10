package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.AllProductRequest;
import com.emenu.features.product.dto.request.CreateProductRequest;
import com.emenu.features.product.dto.request.UpdateProductRequest;
import com.emenu.features.product.dto.response.AllProductResponseDto;
import com.emenu.features.product.dto.response.ProductDto;
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
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());
        ProductDto response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request) {
        log.info("Updating product: {}", id);
        ProductDto response = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id) {
        log.info("Deleting product: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable UUID id) {
        log.info("Fetching product: {}", id);
        ProductDto response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Product retrieved successfully", response));
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<AllProductResponseDto>> getAllProducts(@RequestBody AllProductRequest request) {
        log.info("Fetching all products");
        AllProductResponseDto responses = productService.getAllProducts(request);
        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", responses));
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<ApiResponse<Void>> incrementProductView(@PathVariable UUID id) {
        log.info("Incrementing product view: {}", id);
        productService.incrementProductView(id);
        return ResponseEntity.ok(ApiResponse.success("Product view incremented successfully", null));
    }
}
