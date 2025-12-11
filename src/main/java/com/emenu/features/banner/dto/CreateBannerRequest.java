package com.emenu.features.banner.dto;

import com.emenu.enums.common.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBannerRequest {

    @NotBlank(message = "Banner name is required")
    private String name;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String linkUrl;

    private Integer displayOrder;

    @NotNull(message = "Status is required")
    private Status status;

    private String description;
}
