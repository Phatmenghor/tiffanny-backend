package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.CreateProductPromotionRequest;
import com.emenu.features.product.dto.request.UpdateProductPromotionRequest;
import com.emenu.features.product.dto.response.AllProductPromotionResponseDto;
import com.emenu.features.product.dto.response.ProductPromotionDto;
import com.emenu.features.product.models.ProductPromotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductPromotionMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productId", source = "product.id")
    ProductPromotionDto toDto(ProductPromotion promotion);

    ProductPromotion toEntity(CreateProductPromotionRequest request);

    @Mapping(target = "product", ignore = true)
    void updateEntity(UpdateProductPromotionRequest request, @MappingTarget ProductPromotion promotion);

    @Named("mapToListDto")
    default AllProductPromotionResponseDto mapToListDto(List<ProductPromotionDto> content, Page<ProductPromotion> page) {
        AllProductPromotionResponseDto response = new AllProductPromotionResponseDto();
        response.setContent(content);
        response.setPageNo(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }
}
