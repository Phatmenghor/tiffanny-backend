package com.emenu.features.product.mapper;

import com.emenu.features.product.dto.request.CategoryRequest;
import com.emenu.features.product.dto.response.CategoryResponse;
import com.emenu.features.product.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setImageUrl(category.getImageUrl());
        response.setStatus(category.getStatus());

        // Calculate counts
        response.setSubCategoryCount(
            category.getSubCategories() != null 
                ? (long) category.getSubCategories().stream()
                    .filter(sc -> !sc.getIsDeleted())
                    .count()
                : 0L
        );
        response.setProductCount(
            category.getProducts() != null 
                ? (long) category.getProducts().stream()
                    .filter(p -> !p.getIsDeleted())
                    .count()
                : 0L
        );

        // Audit fields
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        response.setCreatedBy(category.getCreatedBy());
        response.setUpdatedBy(category.getUpdatedBy());

        return response;
    }

    public Category toEntity(CategoryRequest request) {
        if (request == null) {
            return null;
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setImageUrl(request.getImageUrl());
        category.setStatus(request.getStatus());

        return category;
    }

    public void updateEntity(CategoryRequest request, Category category) {
        if (request == null || category == null) {
            return;
        }

        category.setName(request.getName());
        category.setImageUrl(request.getImageUrl());
        category.setStatus(request.getStatus());
    }
}
