package com.emenu.features.product.dto.response;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SubCategoryResponse {

    private UUID id;
    private String name;
    private String imageUrl;
    private Status status;
    private String categoryName;
    private UUID categoryId;
    private Long productCount;

    // Audit fields from BaseUUIDEntity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
