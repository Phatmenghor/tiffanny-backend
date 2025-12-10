package com.emenu.features.product.service.impl;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.AllSubCategoryRequest;
import com.emenu.features.product.dto.request.CreateSubCategoryRequest;
import com.emenu.features.product.dto.request.UpdateSubCategoryRequest;
import com.emenu.features.product.dto.response.AllSubCategoryResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public AllSubCategoryResponseDto getAllSubCategories(AllSubCategoryRequest request) {
        log.info("Fetching all subcategories with filters");
        Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        var spec = SubCategorySpecification.filterSubCategories(
                request.getSearch(),
                request.getStatus(),
                request.getCategoryId(),
                null,  // createdFrom
                null,  // createdTo
                 null  // isDeleted
        );

        Page<SubCategory> page = subCategoryRepository.findAll(spec, pageable);

        List<SubCategoryDto> content = page.stream()
                .map(subCategoryMapper::toDto)
                .toList();

        return subCategoryMapper.mapToListDto(content, page);
    }
}
