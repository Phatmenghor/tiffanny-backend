package com.emenu.features.product.dto.filter;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SubCategoryFilterRequest {

    private String searchTerm;
    private Status status;
    private UUID categoryId;
    private String categoryName;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;
}
