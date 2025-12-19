package com.emenu.features.product.mapper;

import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.CreateProductRequest;
import com.emenu.features.product.dto.request.UpdateProductRequest;
import com.emenu.features.product.dto.response.AllProductResponseDto;
import com.emenu.features.product.dto.response.ProductAttributeResponse;
import com.emenu.features.product.dto.response.ProductDto;
import com.emenu.features.product.dto.response.ProductVariantResponse;
import com.emenu.features.product.models.Category;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.models.ProductAttribute;
import com.emenu.features.product.models.ProductVariant;
import com.emenu.features.product.models.SubCategory;
import com.emenu.features.product.repository.CategoryRepository;
import com.emenu.features.product.repository.SubCategoryRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductMapper {

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected SubCategoryRepository subCategoryRepository;

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "subCategoryName", source = "subCategory.name")
    @Mapping(target = "subCategoryId", source = "subCategory.id")
    @Mapping(target = "attributes", expression = "java(mapAttributes(product))")
    @Mapping(target = "variants", expression = "java(mapVariants(product))")
    @Mapping(target = "suggestions", expression = "java(mapSuggestions(product))")
    public abstract ProductDto toDto(Product product);

    public abstract ProductAttributeResponse toAttributeResponse(ProductAttribute attribute);

    public abstract ProductVariantResponse toVariantResponse(ProductVariant variant);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subCategory", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    public abstract Product toEntity(CreateProductRequest request);

    @AfterMapping
    protected void setRelationshipsAfterCreate(CreateProductRequest request, @MappingTarget Product product) {
        // Set category
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
        product.setCategory(category);

        // Set subcategory if provided
        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(request.getSubCategoryId())
                    .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }

        // Set attributes
        if (request.getAttributes() != null && !request.getAttributes().isEmpty()) {
            request.getAttributes().forEach(attrReq -> {
                ProductAttribute attribute = new ProductAttribute();
                attribute.setAttributeName(attrReq.getAttributeName());
                attribute.setAttributeValue(attrReq.getAttributeValue());
                attribute.setProduct(product);
                product.getAttributes().add(attribute);
            });
        }

        // Set variants
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            request.getVariants().forEach(varReq -> {
                ProductVariant variant = new ProductVariant();
                variant.setName(varReq.getName());
                variant.setPrice(varReq.getPrice());
                variant.setStock(varReq.getStock());
                variant.setDiscount(varReq.getDiscount());
                variant.setDiscountType(varReq.getDiscountType());
                if (varReq.getDiscountStartDate() != null) {
                    variant.setDiscountStartDate(varReq.getDiscountStartDate().atStartOfDay());
                }
                if (varReq.getDiscountEndDate() != null) {
                    variant.setDiscountEndDate(varReq.getDiscountEndDate().atTime(23, 59, 59));
                }
                variant.setImageCover(varReq.getImageCover());
                variant.setProduct(product);
                product.getVariants().add(variant);
            });
        }
    }

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subCategory", ignore = true)
    public abstract void updateEntity(UpdateProductRequest request, @MappingTarget Product product);

    @AfterMapping
    protected void setRelationshipsAfterUpdate(UpdateProductRequest request, @MappingTarget Product product) {
        // Update category if changed
        if (request.getCategoryId() != null && !product.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
            product.setCategory(category);
        }

        // Update subcategory if changed
        if (request.getSubCategoryId() != null) {
            if (product.getSubCategory() == null || !product.getSubCategory().getId().equals(request.getSubCategoryId())) {
                SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(request.getSubCategoryId())
                        .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + request.getSubCategoryId()));
                product.setSubCategory(subCategory);
            }
        }

        // Update attributes - clear and re-add IF provided
        if (request.getAttributes() != null) {
            product.getAttributes().clear();
            if (!request.getAttributes().isEmpty()) {
                request.getAttributes().forEach(attrReq -> {
                    ProductAttribute attribute = new ProductAttribute();
                    attribute.setAttributeName(attrReq.getAttributeName());
                    attribute.setAttributeValue(attrReq.getAttributeValue());
                    attribute.setProduct(product);
                    product.getAttributes().add(attribute);
                });
            }
        }

        // Update variants - clear and re-add IF provided
        if (request.getVariants() != null) {
            product.getVariants().clear();
            if (!request.getVariants().isEmpty()) {
                request.getVariants().forEach(varReq -> {
                    ProductVariant variant = new ProductVariant();
                    variant.setName(varReq.getName());
                    variant.setPrice(varReq.getPrice());
                    variant.setStock(varReq.getStock());
                    variant.setDiscount(varReq.getDiscount());
                    variant.setDiscountType(varReq.getDiscountType());
                    if (varReq.getDiscountStartDate() != null) {
                        variant.setDiscountStartDate(varReq.getDiscountStartDate().atStartOfDay());
                    }
                    if (varReq.getDiscountEndDate() != null) {
                        variant.setDiscountEndDate(varReq.getDiscountEndDate().atTime(23, 59, 59));
                    }
                    variant.setImageCover(varReq.getImageCover());
                    variant.setProduct(product);
                    product.getVariants().add(variant);
                });
            }
        }
    }

    @Named("mapToListDto")
    public AllProductResponseDto mapToListDto(List<ProductDto> content, Page<Product> page) {
        AllProductResponseDto response = new AllProductResponseDto();
        response.setContent(content);
        response.setPageNo(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }

    public List<ProductAttributeResponse> mapAttributes(Product product) {
        if (product == null || product.getAttributes() == null) {
            return Collections.emptyList();
        }
        return product.getAttributes().stream()
                .filter(attr -> Boolean.FALSE.equals(attr.getIsDeleted()))
                .map(this::toAttributeResponse)
                .collect(Collectors.toList());
    }

    public List<ProductVariantResponse> mapVariants(Product product) {
        if (product == null || product.getVariants() == null) {
            return Collections.emptyList();
        }
        return product.getVariants().stream()
                .filter(variant -> Boolean.FALSE.equals(variant.getIsDeleted()))
                .map(this::toVariantResponse)
                .collect(Collectors.toList());
    }

    public List<ProductDto> mapSuggestions(Product product) {
        if (product == null || product.getSuggestions() == null) {
            return Collections.emptyList();
        }
        return product.getSuggestions().stream()
                .map(suggestion -> {
                    Product p = suggestion.getSuggestedProduct();
                    ProductDto dto = new ProductDto();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    dto.setImageUrl(p.getImageUrl());
                    dto.setBasePrice(p.getBasePrice());
                    dto.setStatus(p.getStatus());
                    if (p.getCategory() != null) {
                        dto.setCategoryId(p.getCategory().getId());
                        dto.setCategoryName(p.getCategory().getName());
                    }
                    if (p.getSubCategory() != null) {
                         dto.setSubCategoryId(p.getSubCategory().getId());
                         dto.setSubCategoryName(p.getSubCategory().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
