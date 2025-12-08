package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.CategoryRequest;
import com.emenu.features.product.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(UUID id, CategoryRequest request);

    void deleteCategory(UUID id);

    CategoryResponse getCategoryById(UUID id);

    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getCategoriesByStatus(Status status);
}
