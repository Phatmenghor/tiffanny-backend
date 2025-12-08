package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.SubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryResponse;
import com.emenu.features.product.mapper.SubCategoryMapper;
import com.emenu.features.product.models.Category;
import com.emenu.features.product.models.SubCategory;
import com.emenu.features.product.repository.CategoryRepository;
import com.emenu.features.product.repository.SubCategoryRepository;
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
    public SubCategoryResponse createSubCategory(SubCategoryRequest request) {
        log.info("Creating new subcategory: {}", request.getName());
        
        Category category = categoryRepository.findByIdAndNotDeleted(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
        
        SubCategory subCategory = subCategoryMapper.toEntity(request);
        subCategory.setCategory(category);
        
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        
        log.info("SubCategory created with ID: {}", savedSubCategory.getId());
        return subCategoryMapper.toResponse(savedSubCategory);
    }

    @Override
    @Transactional
    public SubCategoryResponse updateSubCategory(UUID id, SubCategoryRequest request) {
        log.info("Updating subcategory with ID: {}", id);
        
        SubCategory subCategory = subCategoryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + id));
        
        // Update category if changed
        if (!subCategory.getCategory().getId().equals(request.getCategoryId())) {
            Category category = categoryRepository.findByIdAndNotDeleted(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + request.getCategoryId()));
            subCategory.setCategory(category);
        }
        
        subCategoryMapper.updateEntity(request, subCategory);
        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        
        log.info("SubCategory updated: {}", id);
        return subCategoryMapper.toResponse(updatedSubCategory);
    }

    @Override
    @Transactional
    public void deleteSubCategory(UUID id) {
        log.info("Deleting subcategory with ID: {}", id);
        
        SubCategory subCategory = subCategoryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + id));
        
        subCategory.softDelete();
        subCategoryRepository.save(subCategory);
        
        log.info("SubCategory soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public SubCategoryResponse getSubCategoryById(UUID id) {
        log.info("Fetching subcategory with ID: {}", id);
        
        SubCategory subCategory = subCategoryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("SubCategory not found with ID: " + id));
        
        return subCategoryMapper.toResponse(subCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryResponse> getAllSubCategories() {
        log.info("Fetching all active subcategories");
        
        return subCategoryRepository.findAllActive().stream()
                .map(subCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryResponse> getSubCategoriesByCategory(UUID categoryId) {
        log.info("Fetching subcategories for category ID: {}", categoryId);
        
        return subCategoryRepository.findByCategoryId(categoryId).stream()
                .map(subCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryResponse> getSubCategoriesByStatus(Status status) {
        log.info("Fetching subcategories with status: {}", status);
        
        return subCategoryRepository.findByStatus(status).stream()
                .map(subCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
