package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class AllProductPromotionRequest {
    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = 10;
    
    private String search;
    private Status status;
    private UUID productId;
}
