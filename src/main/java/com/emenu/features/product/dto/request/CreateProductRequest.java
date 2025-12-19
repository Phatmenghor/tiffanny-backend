package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal basePrice;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    private UUID subCategoryId;

    @Valid
    private List<ProductAttributeRequest> attributes = new ArrayList<>();

    @Valid
    private List<ProductVariantRequest> variants = new ArrayList<>();

    private List<UUID> suggestionIds;
}
