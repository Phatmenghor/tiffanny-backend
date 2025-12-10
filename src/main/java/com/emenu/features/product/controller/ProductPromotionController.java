package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.AllProductPromotionRequest;
import com.emenu.features.product.dto.request.CreateProductPromotionRequest;
import com.emenu.features.product.dto.request.UpdateProductPromotionRequest;
import com.emenu.features.product.dto.response.AllProductPromotionResponseDto;
import com.emenu.features.product.dto.response.ProductPromotionDto;
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
    public ResponseEntity<ApiResponse<ProductPromotionDto>> createPromotion(@Valid @RequestBody CreateProductPromotionRequest request) {
        log.info("Creating promotion: {}", request.getName());
        ProductPromotionDto response = promotionService.createPromotion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Promotion created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductPromotionDto>> updatePromotion(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductPromotionRequest request) {
        log.info("Updating promotion: {}", id);
        ProductPromotionDto response = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(ApiResponse.success("Promotion updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePromotion(@PathVariable UUID id) {
        log.info("Deleting promotion: {}", id);
        promotionService.deletePromotion(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductPromotionDto>> getPromotionById(@PathVariable UUID id) {
        log.info("Fetching promotion: {}", id);
        ProductPromotionDto response = promotionService.getPromotionById(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AllProductPromotionResponseDto>> getAllPromotions(@RequestBody AllProductPromotionRequest request) {
        log.info("Fetching all promotions");
        AllProductPromotionResponseDto responses = promotionService.getAllPromotions(request);
        return ResponseEntity.ok(ApiResponse.success("Promotions retrieved successfully", responses));
    }
}
