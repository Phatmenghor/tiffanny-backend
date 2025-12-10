package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.filter.CategoryFilterRequest;
import com.emenu.features.product.dto.request.AllCategoryRequest;
import com.emenu.features.product.dto.request.CreateCategoryRequest;
import com.emenu.features.product.dto.request.UpdateCategoryRequest;
import com.emenu.features.product.dto.response.AllCategoryResponseDto;
import com.emenu.features.product.dto.response.CategoryDto;
import com.emenu.features.product.service.CategoryService;
import com.emenu.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        log.info("Creating category: {}", request.getName());
        CategoryDto response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @PathVariable UUID id,
            @RequestBody UpdateCategoryRequest request) {
        log.info("Updating category: {}", id);
        CategoryDto response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        log.info("Deleting category: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable UUID id) {
        log.info("Fetching category: {}", id);
        CategoryDto response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AllCategoryResponseDto>> getAllCategories(@RequestBody AllCategoryRequest request) {
        log.info("Fetching all categories");
        AllCategoryResponseDto responses = categoryService.getAllCategories(request);
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", responses));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategoriesByStatus(@PathVariable Status status) {
        log.info("Fetching categories by status: {}", status);
        List<CategoryDto> responses = categoryService.getCategoriesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", responses));
    }

    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<Page<CategoryDto>>> filterCategories(
            @RequestBody CategoryFilterRequest filter,
            Pageable pageable) {
        log.info("Filtering categories with: {}", filter);
        Page<CategoryDto> responses = categoryService.filterCategories(filter, pageable);
        return ResponseEntity.ok(ApiResponse.success("Categories filtered successfully", responses));
    }
}
