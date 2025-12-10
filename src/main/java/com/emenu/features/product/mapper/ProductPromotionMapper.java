package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.ProductPromotionRequest;
import com.emenu.features.product.dto.response.ProductPromotionResponse;
import com.emenu.features.product.models.ProductPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductPromotionMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productId", source = "product.id")
    ProductPromotionResponse toResponse(ProductPromotion promotion);

    ProductPromotion toEntity(ProductPromotionRequest request);

    @Mapping(target = "product", ignore = true)
    void updateEntity(ProductPromotionRequest request, @MappingTarget ProductPromotion promotion);
}
