package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.CreateSubCategoryRequest;
import com.emenu.features.product.dto.request.UpdateSubCategoryRequest;
import com.emenu.features.product.dto.response.AllSubCategoryResponseDto;
import com.emenu.features.product.dto.response.SubCategoryDto;
import com.emenu.features.product.models.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubCategoryMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "productCount", expression = "java(calculateProductCount(subCategory))")
    SubCategoryDto toDto(SubCategory subCategory);

    SubCategory toEntity(CreateSubCategoryRequest request);

    void updateEntity(UpdateSubCategoryRequest request, @MappingTarget SubCategory subCategory);

    @Named("mapToListDto")
    default AllSubCategoryResponseDto mapToListDto(List<SubCategoryDto> content, Page<SubCategory> page) {
        AllSubCategoryResponseDto response = new AllSubCategoryResponseDto();
        response.setContent(content);
        response.setPageNo(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }

    default Long calculateProductCount(SubCategory subCategory) {
        if (subCategory == null || subCategory.getProducts() == null) {
            return 0L;
        }
        return subCategory.getProducts().stream()
                .filter(p -> Boolean.FALSE.equals(p.getIsDeleted()))
                .count();
    }
}
