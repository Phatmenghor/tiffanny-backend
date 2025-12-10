package com.emenu.features.product.service.impl;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.AlreadyExistException;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.filter.CategoryFilterRequest;
import com.emenu.features.product.dto.request.AllCategoryRequest;
import com.emenu.features.product.dto.request.CreateCategoryRequest;
import com.emenu.features.product.dto.request.UpdateCategoryRequest;
import com.emenu.features.product.dto.response.AllCategoryResponseDto;
import com.emenu.features.product.dto.response.CategoryDto;
import com.emenu.features.product.mapper.CategoryMapper;
import com.emenu.features.product.models.Category;
import com.emenu.features.product.repository.CategoryRepository;
import com.emenu.features.product.service.CategoryService;
import com.emenu.features.product.specification.CategorySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public CategoryDto createCategory(CreateCategoryRequest request) {
        log.info("Creating new category: {}", request.getName());

        if(categoryRepository.existByName(request.getName())) {
            log.info("Category with name {} failed to create", request.getName());
            throw new AlreadyExistException("Category with name " + request.getName() + " Already exist!");
        }

        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        
        log.info("Category created with ID: {}", savedCategory.getId());
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(UUID id, UpdateCategoryRequest request) {
        log.info("Updating category with ID: {}", id);
        
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        
        categoryMapper.updateEntity(request, category);
        Category updatedCategory = categoryRepository.save(category);
        
        log.info("Category updated: {}", id);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        log.info("Deleting category with ID: {}", id);
        
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        
        category.softDelete();
        categoryRepository.save(category);
        
        log.info("Category soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(UUID id) {
        log.info("Fetching category with ID: {}", id);
        
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public AllCategoryResponseDto getAllCategories(AllCategoryRequest request) {
        log.info("Fetching all active categories");
        Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        var spec = CategorySpec.hasStatus(request.getStatus())
                .and(CategorySpec.searchByName(request.getSearch()));

        Page<Category> page = categoryRepository.findAll(spec,pageable);

        List<CategoryDto> content = page.stream()
                .map(categoryMapper::toDto)
                .toList();

        return categoryMapper.mapToListDto(content, page);
    }
}
