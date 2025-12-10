package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.CreateCategoryRequest;
import com.emenu.features.product.dto.request.UpdateCategoryRequest;
import com.emenu.features.product.dto.response.AllCategoryResponseDto;
import com.emenu.features.product.dto.response.CategoryDto;
import com.emenu.features.product.models.Category;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "subCategoryCount", expression = "java(calculateSubCategoryCount(category))")
    @Mapping(target = "productCount", expression = "java(calculateProductCount(category))")
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequest request);

    void updateEntity(UpdateCategoryRequest request, @MappingTarget Category category);

    @Named("mapToListDto")
    default AllCategoryResponseDto mapToListDto(List<CategoryDto> content, Page<Category> legalTypePage) {
        AllCategoryResponseDto response = new AllCategoryResponseDto();
        response.setContent(content);
        response.setPageNo(legalTypePage.getNumber() + 1);
        response.setPageSize(legalTypePage.getSize());
        response.setTotalElements(legalTypePage.getTotalElements());
        response.setTotalPages(legalTypePage.getTotalPages());
        response.setLast(legalTypePage.isLast());
        return response;
    }

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
