package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SubCategoryRequest {

    @NotBlank(message = "SubCategory name is required")
    private String name;

    private String imageUrl;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;
}
