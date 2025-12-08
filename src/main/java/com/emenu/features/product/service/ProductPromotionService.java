package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.ProductPromotionRequest;
import com.emenu.features.product.dto.response.ProductPromotionResponse;

import java.util.List;
import java.util.UUID;

public interface ProductPromotionService {

    ProductPromotionResponse createPromotion(ProductPromotionRequest request);

    ProductPromotionResponse updatePromotion(UUID id, ProductPromotionRequest request);

    void deletePromotion(UUID id);

    ProductPromotionResponse getPromotionById(UUID id);

    List<ProductPromotionResponse> getAllPromotions();

    List<ProductPromotionResponse> getPromotionByProduct(UUID productId);

    List<ProductPromotionResponse> getPromotionsByStatus(Status status);
}
