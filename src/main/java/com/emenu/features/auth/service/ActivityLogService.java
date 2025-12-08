package com.emenu.features.auth.service;

import com.emenu.features.auth.dto.filter.ActivityLogFilterRequest;
import com.emenu.features.auth.dto.response.ActivityLogResponse;
import com.emenu.shared.dto.PaginationResponse;

import java.util.UUID;

public interface ActivityLogService {
    
    /**
     * Get all activity logs with filtering and pagination
     */
    PaginationResponse<ActivityLogResponse> getActivityLogs(ActivityLogFilterRequest request);
    
    /**
     * Get activity log by ID
     */
    ActivityLogResponse getActivityLogById(UUID logId);
}
