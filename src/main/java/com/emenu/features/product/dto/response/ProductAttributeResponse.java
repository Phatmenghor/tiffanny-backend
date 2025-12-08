package com.emenu.features.product.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductAttributeResponse {

    private UUID id;
    private String attributeName;
    private String attributeValue;

    // Audit fields from BaseUUIDEntity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
