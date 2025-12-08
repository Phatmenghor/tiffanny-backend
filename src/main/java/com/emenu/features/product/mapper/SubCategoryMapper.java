package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.SubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryResponse;
import com.emenu.features.product.models.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryMapper {

    public SubCategoryResponse toResponse(SubCategory subCategory) {
        if (subCategory == null) {
            return null;
        }

        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(subCategory.getId());
        response.setName(subCategory.getName());
        response.setImageUrl(subCategory.getImageUrl());
        response.setStatus(subCategory.getStatus());

        // Category information
        if (subCategory.getCategory() != null) {
            response.setCategoryId(subCategory.getCategory().getId());
            response.setCategoryName(subCategory.getCategory().getName());
        }

        // Calculate product count
        response.setProductCount(
            subCategory.getProducts() != null 
                ? (long) subCategory.getProducts().stream()
                    .filter(p -> !p.getIsDeleted())
                    .count()
                : 0L
        );

        // Audit fields
        response.setCreatedAt(subCategory.getCreatedAt());
        response.setUpdatedAt(subCategory.getUpdatedAt());
        response.setCreatedBy(subCategory.getCreatedBy());
        response.setUpdatedBy(subCategory.getUpdatedBy());

        return response;
    }

    public SubCategory toEntity(SubCategoryRequest request) {
        if (request == null) {
            return null;
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.getName());
        subCategory.setImageUrl(request.getImageUrl());
        subCategory.setStatus(request.getStatus());

        return subCategory;
    }

    public void updateEntity(SubCategoryRequest request, SubCategory subCategory) {
        if (request == null || subCategory == null) {
            return;
        }

        subCategory.setName(request.getName());
        subCategory.setImageUrl(request.getImageUrl());
        subCategory.setStatus(request.getStatus());
    }
}
