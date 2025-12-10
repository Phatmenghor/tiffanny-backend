package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.filter.CategoryFilterRequest;
import com.emenu.features.product.dto.request.AllCategoryRequest;
import com.emenu.features.product.dto.request.CreateCategoryRequest;
import com.emenu.features.product.dto.request.UpdateCategoryRequest;
import com.emenu.features.product.dto.response.AllCategoryResponseDto;
import com.emenu.features.product.dto.response.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryDto createCategory(CreateCategoryRequest request);

    CategoryDto updateCategory(UUID id, UpdateCategoryRequest request);

    void deleteCategory(UUID id);

    CategoryDto getCategoryById(UUID id);

    AllCategoryResponseDto getAllCategories(AllCategoryRequest request);
}
