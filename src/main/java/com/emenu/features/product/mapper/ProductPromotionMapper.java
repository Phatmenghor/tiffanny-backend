package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.ProductPromotionRequest;
import com.emenu.features.product.dto.response.ProductPromotionResponse;
import com.emenu.features.product.models.ProductPromotion;
import org.springframework.stereotype.Component;

@Component
public class ProductPromotionMapper {

    public ProductPromotionResponse toResponse(ProductPromotion promotion) {
        if (promotion == null) {
            return null;
        }

        ProductPromotionResponse response = new ProductPromotionResponse();
        response.setId(promotion.getId());
        response.setName(promotion.getName());
        response.setDescription(promotion.getDescription());
        response.setImageUrl(promotion.getImageUrl());
        response.setProductView(promotion.getProductView());
        response.setStatus(promotion.getStatus());

        // Product information
        if (promotion.getProduct() != null) {
            response.setProductId(promotion.getProduct().getId());
            response.setProductName(promotion.getProduct().getName());
        }

        // Audit fields
        response.setCreatedAt(promotion.getCreatedAt());
        response.setUpdatedAt(promotion.getUpdatedAt());
        response.setCreatedBy(promotion.getCreatedBy());
        response.setUpdatedBy(promotion.getUpdatedBy());

        return response;
    }

    public ProductPromotion toEntity(ProductPromotionRequest request) {
        if (request == null) {
            return null;
        }

        ProductPromotion promotion = new ProductPromotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setImageUrl(request.getImageUrl());
        promotion.setStatus(request.getStatus());

        return promotion;
    }

    public void updateEntity(ProductPromotionRequest request, ProductPromotion promotion) {
        if (request == null || promotion == null) {
            return;
        }

        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setImageUrl(request.getImageUrl());
        promotion.setStatus(request.getStatus());
    }
}
