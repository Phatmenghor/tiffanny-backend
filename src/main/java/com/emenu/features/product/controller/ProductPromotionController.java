package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.ProductPromotionRequest;
import com.emenu.features.product.dto.response.ProductPromotionResponse;
import com.emenu.features.product.service.ProductPromotionService;
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
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Slf4j
public class ProductPromotionController {

    private final ProductPromotionService promotionService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductPromotionResponse>> createPromotion(@Valid @RequestBody ProductPromotionRequest request) {
        log.info("Creating promotion: {}", request.getName());
        ProductPromotionResponse response = promotionService.createPromotion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Promotion created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductPromotionResponse>> updatePromotion(
            @PathVariable UUID id,
            @Valid @RequestBody ProductPromotionRequest request) {
        log.info("Updating promotion: {}", id);
        ProductPromotionResponse response = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(ApiResponse.success("Promotion updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePromotion(@PathVariable UUID id) {
        log.info("Deleting promotion: {}", id);
        promotionService.deletePromotion(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductPromotionResponse>> getPromotionById(@PathVariable UUID id) {
        log.info("Fetching promotion: {}", id);
        ProductPromotionResponse response = promotionService.getPromotionById(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductPromotionResponse>>> getAllPromotions() {
        log.info("Fetching all promotions");
        List<ProductPromotionResponse> responses = promotionService.getAllPromotions();
        return ResponseEntity.ok(ApiResponse.success("Promotions retrieved successfully", responses));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<ProductPromotionResponse>> getPromotionByProduct(@PathVariable UUID productId) {
        log.info("Fetching promotion by product: {}", productId);
        ProductPromotionResponse response = promotionService.getPromotionByProduct(productId);
        return ResponseEntity.ok(ApiResponse.success("Promotion retrieved successfully", response));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ProductPromotionResponse>>> getPromotionsByStatus(@PathVariable Status status) {
        log.info("Fetching promotions by status: {}", status);
        List<ProductPromotionResponse> responses = promotionService.getPromotionsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Promotions retrieved successfully", responses));
    }
}
