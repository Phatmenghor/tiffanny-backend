package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.AllSubCategoryRequest;
import com.emenu.features.product.dto.request.CreateSubCategoryRequest;
import com.emenu.features.product.dto.request.UpdateSubCategoryRequest;
import com.emenu.features.product.dto.response.AllSubCategoryResponseDto;
import com.emenu.features.product.dto.response.SubCategoryDto;

import java.util.List;
import java.util.UUID;

public interface SubCategoryService {

    SubCategoryDto createSubCategory(CreateSubCategoryRequest request);

    SubCategoryDto updateSubCategory(UUID id, UpdateSubCategoryRequest request);

    void deleteSubCategory(UUID id);

    SubCategoryDto getSubCategoryById(UUID id);

    AllSubCategoryResponseDto getAllSubCategories(AllSubCategoryRequest request);
}
