package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.SubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryResponse;
import com.emenu.features.product.models.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "productCount", expression = "java(calculateProductCount(subCategory))")
    SubCategoryResponse toResponse(SubCategory subCategory);

    SubCategory toEntity(SubCategoryRequest request);

    void updateEntity(SubCategoryRequest request, @MappingTarget SubCategory subCategory);

    default Long calculateProductCount(SubCategory subCategory) {
        if (subCategory == null || subCategory.getProducts() == null) {
            return 0L;
        }
        return subCategory.getProducts().stream()
                .filter(p -> Boolean.FALSE.equals(p.getIsDeleted()))
                .count();
    }
}
