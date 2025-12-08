package com.emenu.features.product.dto.response;

import com.emenu.enums.product.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductVariantResponse {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private BigDecimal discount;
    private DiscountType discountType;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;
    private String imageCover;

    // Audit fields from BaseUUIDEntity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
