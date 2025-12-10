package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateProductRequest {

    private String name;
    private String description;
    private String imageUrl;
    private Status status;
    private UUID categoryId;
    private UUID subCategoryId;

    @Valid
    private List<ProductAttributeRequest> attributes = new ArrayList<>();

    @Valid
    private List<ProductVariantRequest> variants = new ArrayList<>();
}
