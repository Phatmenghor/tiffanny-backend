package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllProductRequest {
    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = 10;
    
    private String search;
    private Status status;
    private UUID categoryId;
    private UUID subCategoryId;
}
