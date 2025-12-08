package com.emenu.features.product.dto.filter;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductFilterRequest {

    private String searchTerm;
    private Status status;
    private UUID categoryId;
    private UUID subCategoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean inStock;
    private Long minViews;
    private Long maxViews;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;
}
