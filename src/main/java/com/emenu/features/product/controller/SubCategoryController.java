package com.emenu.features.product.controller;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.SubCategoryRequest;
import com.emenu.features.product.dto.response.SubCategoryResponse;
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
    public ResponseEntity<ApiResponse<SubCategoryResponse>> createSubCategory(@Valid @RequestBody SubCategoryRequest request) {
        log.info("Creating subcategory: {}", request.getName());
        SubCategoryResponse response = subCategoryService.createSubCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("SubCategory created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> updateSubCategory(
            @PathVariable UUID id,
            @Valid @RequestBody SubCategoryRequest request) {
        log.info("Updating subcategory: {}", id);
        SubCategoryResponse response = subCategoryService.updateSubCategory(id, request);
        return ResponseEntity.ok(ApiResponse.success("SubCategory updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubCategory(@PathVariable UUID id) {
        log.info("Deleting subcategory: {}", id);
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.ok(ApiResponse.success("SubCategory deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> getSubCategoryById(@PathVariable UUID id) {
        log.info("Fetching subcategory: {}", id);
        SubCategoryResponse response = subCategoryService.getSubCategoryById(id);
        return ResponseEntity.ok(ApiResponse.success("SubCategory retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getAllSubCategories() {
        log.info("Fetching all subcategories");
        List<SubCategoryResponse> responses = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(ApiResponse.success("SubCategories retrieved successfully", responses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getSubCategoriesByCategory(@PathVariable UUID categoryId) {
        log.info("Fetching subcategories by category: {}", categoryId);
        List<SubCategoryResponse> responses = subCategoryService.getSubCategoriesByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("SubCategories retrieved successfully", responses));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getSubCategoriesByStatus(@PathVariable Status status) {
        log.info("Fetching subcategories by status: {}", status);
        List<SubCategoryResponse> responses = subCategoryService.getSubCategoriesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("SubCategories retrieved successfully", responses));
    }
}
