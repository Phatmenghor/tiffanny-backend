package com.emenu.features.product.dto.response;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProductResponse {

    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private Long productView;
    private Status status;

    private String categoryName;
    private UUID categoryId;
    private String subCategoryName;
    private UUID subCategoryId;

    private List<ProductAttributeResponse> attributes = new ArrayList<>();
    private List<ProductVariantResponse> variants = new ArrayList<>();

    // Audit fields from BaseUUIDEntity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
