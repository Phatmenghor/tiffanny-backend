package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.CategoryRequest;
import com.emenu.features.product.dto.response.CategoryResponse;
import com.emenu.features.product.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "subCategoryCount", expression = "java(calculateSubCategoryCount(category))")
    @Mapping(target = "productCount", expression = "java(calculateProductCount(category))")
    CategoryResponse toResponse(Category category);

    Category toEntity(CategoryRequest request);

    void updateEntity(CategoryRequest request, @MappingTarget Category category);

    default Long calculateSubCategoryCount(Category category) {
        if (category == null || category.getSubCategories() == null) {
            return 0L;
        }
        return category.getSubCategories().stream()
                .filter(sc -> Boolean.FALSE.equals(sc.getIsDeleted()))
                .count();
    }

    default Long calculateProductCount(Category category) {
        if (category == null || category.getProducts() == null) {
            return 0L;
        }
        return category.getProducts().stream()
                .filter(p -> Boolean.FALSE.equals(p.getIsDeleted()))
                .count();
    }
}
