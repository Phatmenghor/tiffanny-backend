package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.CreateProductRequest;
import com.emenu.features.product.dto.request.UpdateProductRequest;
import com.emenu.features.product.dto.response.AllProductResponseDto;
import com.emenu.features.product.dto.response.ProductAttributeResponse;
import com.emenu.features.product.dto.response.ProductDto;
import com.emenu.features.product.dto.response.ProductVariantResponse;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.models.ProductAttribute;
import com.emenu.features.product.models.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "subCategoryName", source = "subCategory.name")
    @Mapping(target = "subCategoryId", source = "subCategory.id")
    @Mapping(target = "attributes", expression = "java(mapAttributes(product))")
    @Mapping(target = "variants", expression = "java(mapVariants(product))")
    ProductDto toDto(Product product);

    ProductAttributeResponse toAttributeResponse(ProductAttribute attribute);

    ProductVariantResponse toVariantResponse(ProductVariant variant);

    Product toEntity(CreateProductRequest request);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subCategory", ignore = true)
    void updateEntity(UpdateProductRequest request, @MappingTarget Product product);

    @Named("mapToListDto")
    default AllProductResponseDto mapToListDto(List<ProductDto> content, Page<Product> page) {
        AllProductResponseDto response = new AllProductResponseDto();
        response.setContent(content);
        response.setPageNo(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }

    default List<ProductAttributeResponse> mapAttributes(Product product) {
        if (product == null || product.getAttributes() == null) {
            return Collections.emptyList();
        }
        return product.getAttributes().stream()
                .filter(attr -> Boolean.FALSE.equals(attr.getIsDeleted()))
                .map(this::toAttributeResponse)
                .collect(Collectors.toList());
    }

    default List<ProductVariantResponse> mapVariants(Product product) {
        if (product == null || product.getVariants() == null) {
            return Collections.emptyList();
        }
        return product.getVariants().stream()
                .filter(variant -> Boolean.FALSE.equals(variant.getIsDeleted()))
                .map(this::toVariantResponse)
                .collect(Collectors.toList());
    }
}
