package com.emenu.features.auth.dto.filter;

import com.emenu.shared.dto.BaseFilterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityLogFilterRequest extends BaseFilterRequest {
    
    private UUID userId;
    private String clientIp;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String search;
}
