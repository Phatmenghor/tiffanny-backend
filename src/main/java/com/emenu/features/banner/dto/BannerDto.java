package com.emenu.features.banner.dto;

import com.emenu.enums.common.Status;
import lombok.Data;
import java.util.UUID;

@Data
public class BannerDto {
    private UUID id;
    private String name;
    private String imageUrl;
    private String linkUrl;
    private Integer displayOrder;
    private Status status;
    private String description;
}
