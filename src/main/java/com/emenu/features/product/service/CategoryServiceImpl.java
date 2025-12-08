package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.CategoryRequest;
import com.emenu.features.product.dto.response.CategoryResponse;
import com.emenu.features.product.mapper.CategoryMapper;
import com.emenu.features.product.models.Category;
import com.emenu.features.product.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Creating new category: {}", request.getName());
        
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        
        log.info("Category created with ID: {}", savedCategory.getId());
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID id, CategoryRequest request) {
        log.info("Updating category with ID: {}", id);
        
        Category category = categoryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        
        categoryMapper.updateEntity(request, category);
        Category updatedCategory = categoryRepository.save(category);
        
        log.info("Category updated: {}", id);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        log.info("Deleting category with ID: {}", id);
        
        Category category = categoryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        
        category.softDelete();
        categoryRepository.save(category);
        
        log.info("Category soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID id) {
        log.info("Fetching category with ID: {}", id);
        
        Category category = categoryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        log.info("Fetching all active categories");
        
        return categoryRepository.findAllActive().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoriesByStatus(Status status) {
        log.info("Fetching categories with status: {}", status);
        
        return categoryRepository.findByStatus(status).stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
