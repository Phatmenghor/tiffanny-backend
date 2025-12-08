package com.emenu.features.product.dto.response;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductPromotionResponse {

    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private Long productView;
    private Status status;

    private UUID productId;
    private String productName;

    // Audit fields from BaseUUIDEntity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
