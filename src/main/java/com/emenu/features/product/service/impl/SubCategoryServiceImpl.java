package com.emenu.features.product.service.impl;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.CreateSubCategoryRequest;
import com.emenu.features.product.dto.request.UpdateSubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryDto;
import com.emenu.features.product.mapper.SubCategoryMapper;
import com.emenu.features.product.models.Category;
import com.emenu.features.product.models.SubCategory;
import com.emenu.features.product.repository.CategoryRepository;
import com.emenu.features.product.repository.SubCategoryRepository;
import com.emenu.features.product.service.SubCategoryService;
import com.emenu.features.product.specification.SubCategorySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    @Override
    @Transactional
    public SubCategoryDto createSubCategory(CreateSubCategoryRequest request) {
        log.info("Creating new subcategory: {}", request.getName());
        
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
        
        SubCategory subCategory = subCategoryMapper.toEntity(request);
        subCategory.setCategory(category);
        
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        
        log.info("SubCategory created with ID: {}", savedSubCategory.getId());
        return subCategoryMapper.toDto(savedSubCategory);
    }

    @Override
    @Transactional
    public SubCategoryDto updateSubCategory(UUID id, UpdateSubCategoryRequest request) {
        log.info("Updating subcategory with ID: {}", id);
        
        SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + id));
        
        // Update category if changed
        if (request.getCategoryId() != null && !subCategory.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
            subCategory.setCategory(category);
        }
        
        subCategoryMapper.updateEntity(request, subCategory);
        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        
        log.info("SubCategory updated: {}", id);
        return subCategoryMapper.toDto(updatedSubCategory);
    }

    @Override
    @Transactional
    public void deleteSubCategory(UUID id) {
        log.info("Deleting subcategory with ID: {}", id);
        
        SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + id));
        
        subCategory.softDelete();
        subCategoryRepository.save(subCategory);
        
        log.info("SubCategory soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategoryDto getSubCategoryById(UUID id) {
        log.info("Fetching subcategory with ID: {}", id);
        
        SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + id));
        
        return subCategoryMapper.toDto(subCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryDto> getAllSubCategories() {
        log.info("Fetching all active subcategories");
        
        // Use specification to find all active subcategories
        return subCategoryRepository.findAll(
                SubCategorySpecification.filterSubCategories(null, null, null, null, null, null)
        ).stream()
                .map(subCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryDto> getSubCategoriesByCategory(UUID categoryId) {
        log.info("Fetching subcategories for category ID: {}", categoryId);
        
        // Use specification to filter by category
        return subCategoryRepository.findAll(
                SubCategorySpecification.filterSubCategories(null, null, categoryId, null, null, null)
        ).stream()
                .map(subCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryDto> getSubCategoriesByStatus(Status status) {
        log.info("Fetching subcategories with status: {}", status);
        
        // Use specification to filter by status
        return subCategoryRepository.findAll(
                SubCategorySpecification.filterSubCategories(null, status, null, null, null, null)
        ).stream()
                .map(subCategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
