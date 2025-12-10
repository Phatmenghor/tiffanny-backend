package com.emenu.features.product.dto.request;

import com.emenu.enums.common.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private String name;
    private String imageUrl;
    private Status status;
}
