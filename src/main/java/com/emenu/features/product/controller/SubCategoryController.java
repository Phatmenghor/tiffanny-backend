package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.CreateSubCategoryRequest;
import com.emenu.features.product.dto.request.UpdateSubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryDto;
import com.emenu.features.product.service.SubCategoryService;
import com.emenu.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
@Slf4j
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubCategoryDto>> createSubCategory(@Valid @RequestBody CreateSubCategoryRequest request) {
        log.info("Creating subcategory: {}", request.getName());
        SubCategoryDto response = subCategoryService.createSubCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("SubCategory created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoryDto>> updateSubCategory(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSubCategoryRequest request) {
        log.info("Updating subcategory: {}", id);
        SubCategoryDto response = subCategoryService.updateSubCategory(id, request);
        return ResponseEntity.ok(ApiResponse.success("SubCategory updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubCategory(@PathVariable UUID id) {
        log.info("Deleting subcategory: {}", id);
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.ok(ApiResponse.success("SubCategory deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoryDto>> getSubCategoryById(@PathVariable UUID id) {
        log.info("Fetching subcategory: {}", id);
        SubCategoryDto response = subCategoryService.getSubCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success("SubCategory retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubCategoryDto>>> getAllSubCategories() {
        log.info("Fetching all subcategories");
        List<SubCategoryDto> responses = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(ApiResponse.success("SubCategories retrieved successfully", responses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<SubCategoryDto>>> getSubCategoriesByCategory(@PathVariable UUID categoryId) {
        log.info("Fetching subcategories by category: {}", categoryId);
        List<SubCategoryDto> responses = subCategoryService.getSubCategoriesByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("SubCategories retrieved successfully", responses));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<SubCategoryDto>>> getSubCategoriesByStatus(@PathVariable Status status) {
        log.info("Fetching subcategories by status: {}", status);
        List<SubCategoryDto> responses = subCategoryService.getSubCategoriesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("SubCategories retrieved successfully", responses));
    }
}
