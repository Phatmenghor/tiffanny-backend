package com.emenu.features.banner.dto;

import com.emenu.enums.common.Status;
import lombok.Data;

@Data
public class UpdateBannerRequest {
    private String name;
    private String imageUrl;
    private String linkUrl;
    private Integer displayOrder;
    private Status status;
    private String description;
}
