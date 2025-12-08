package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    private String name;

    private String imageUrl;

    @NotNull(message = "Status is required")
    private Status status;
}
