package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductPromotionRequest {

    @NotBlank(message = "Promotion name is required")
    private String name;

    private String description;

    private String imageUrl;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Product ID is required")
    private UUID productId;
}
