package com.emenu.features.product.dto.filter;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryFilterRequest {

    private String searchTerm;
    private Status status;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;
}
