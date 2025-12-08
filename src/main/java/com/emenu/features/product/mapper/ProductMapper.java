package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.ProductRequest;
import com.emenu.features.product.dto.response.ProductAttributeResponse;
import com.emenu.features.product.dto.response.ProductResponse;
import com.emenu.features.product.dto.response.ProductVariantResponse;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.models.ProductAttribute;
import com.emenu.features.product.models.ProductVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setProductView(product.getProductView());
        response.setStatus(product.getStatus());

        // Category information
        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }

        // SubCategory information
        if (product.getSubCategory() != null) {
            response.setSubCategoryId(product.getSubCategory().getId());
            response.setSubCategoryName(product.getSubCategory().getName());
        }

        // Map attributes
        if (product.getAttributes() != null) {
            response.setAttributes(
                product.getAttributes().stream()
                    .filter(attr -> !attr.getIsDeleted())
                    .map(this::toAttributeResponse)
                    .collect(Collectors.toList())
            );
        }

        // Map variants
        if (product.getVariants() != null) {
            response.setVariants(
                product.getVariants().stream()
                    .filter(variant -> !variant.getIsDeleted())
                    .map(this::toVariantResponse)
                    .collect(Collectors.toList())
            );
        }

        // Audit fields
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        response.setCreatedBy(product.getCreatedBy());
        response.setUpdatedBy(product.getUpdatedBy());

        return response;
    }

    public ProductAttributeResponse toAttributeResponse(ProductAttribute attribute) {
        if (attribute == null) {
            return null;
        }

        ProductAttributeResponse response = new ProductAttributeResponse();
        response.setId(attribute.getId());
        response.setAttributeName(attribute.getAttributeName());
        response.setAttributeValue(attribute.getAttributeValue());
        response.setCreatedAt(attribute.getCreatedAt());
        response.setUpdatedAt(attribute.getUpdatedAt());

        return response;
    }

    public ProductVariantResponse toVariantResponse(ProductVariant variant) {
        if (variant == null) {
            return null;
        }

        ProductVariantResponse response = new ProductVariantResponse();
        response.setId(variant.getId());
        response.setName(variant.getName());
        response.setPrice(variant.getPrice());
        response.setStock(variant.getStock());
        response.setDiscount(variant.getDiscount());
        response.setDiscountType(variant.getDiscountType());
        response.setDiscountStartDate(variant.getDiscountStartDate());
        response.setDiscountEndDate(variant.getDiscountEndDate());
        response.setImageCover(variant.getImageCover());
        response.setCreatedAt(variant.getCreatedAt());
        response.setUpdatedAt(variant.getUpdatedAt());

        return response;
    }

    public Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setStatus(request.getStatus());

        return product;
    }

    public void updateEntity(ProductRequest request, Product product) {
        if (request == null || product == null) {
            return;
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setStatus(request.getStatus());
    }
}
