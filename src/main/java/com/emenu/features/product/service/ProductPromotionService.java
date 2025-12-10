package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.AllProductPromotionRequest;
import com.emenu.features.product.dto.request.CreateProductPromotionRequest;
import com.emenu.features.product.dto.request.UpdateProductPromotionRequest;
import com.emenu.features.product.dto.response.AllProductPromotionResponseDto;
import com.emenu.features.product.dto.response.ProductPromotionDto;

import java.util.List;
import java.util.UUID;

public interface ProductPromotionService {

    ProductPromotionDto createPromotion(CreateProductPromotionRequest request);

    ProductPromotionDto updatePromotion(UUID id, UpdateProductPromotionRequest request);

    void deletePromotion(UUID id);

    ProductPromotionDto getPromotionById(UUID id);

    AllProductPromotionResponseDto getAllPromotions(AllProductPromotionRequest request);
}
