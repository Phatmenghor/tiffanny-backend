package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.SubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {

    SubCategoryResponse createSubCategory(SubCategoryRequest request);

    SubCategoryResponse updateSubCategory(UUID id, SubCategoryRequest request);

    void deleteSubCategory(UUID id);

    SubCategoryResponse getSubCategoryById(UUID id);

    List<SubCategoryResponse> getAllSubCategories();

    List<SubCategoryResponse> getSubCategoriesByCategory(UUID categoryId);

    List<SubCategoryResponse> getSubCategoriesByStatus(Status status);
}
