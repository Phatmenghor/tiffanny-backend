package com.emenu.features.auth.service;

import com.emenu.features.auth.dto.filter.ActivityLogFilterRequest;
import com.emenu.features.auth.dto.response.ActivityLogResponse;
import com.emenu.shared.dto.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ActivityLogService {
    
    /**
     * Get all activity logs with filtering and pagination using PaginationResponse
     */
    PaginationResponse<ActivityLogResponse> getActivityLogs(ActivityLogFilterRequest request);
    
    /**
     * Get all activity logs using specification-based filtering (returns Page)
     */
    Page<ActivityLogResponse> filterActivityLogs(ActivityLogFilterRequest filter, Pageable pageable);
    
    /**
     * Get activity log by ID
     */
    ActivityLogResponse getActivityLogById(UUID logId);
}
