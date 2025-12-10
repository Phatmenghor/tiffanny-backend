package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateProductPromotionRequest {
    private String name;
    private String description;
    private String imageUrl;
    private Status status;
    private UUID productId;
}
