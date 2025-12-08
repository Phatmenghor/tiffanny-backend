package com.emenu.features.auth.service.impl;

import com.emenu.features.auth.dto.filter.ActivityLogFilterRequest;
import com.emenu.features.auth.dto.response.ActivityLogResponse;
import com.emenu.features.auth.mapper.ActivityLogMapper;
import com.emenu.features.auth.models.ActivityLog;
import com.emenu.features.auth.repository.ActivityLogRepository;
import com.emenu.features.auth.service.ActivityLogService;
import com.emenu.shared.dto.PaginationResponse;
import com.emenu.shared.mapper.PaginationMapper;
import com.emenu.shared.pagination.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final ActivityLogMapper activityLogMapper;
    private final PaginationMapper paginationMapper;

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<ActivityLogResponse> getActivityLogs(ActivityLogFilterRequest request) {
        log.info("Getting activity logs with filters");

        int pageNo = request.getPageNo() != null && request.getPageNo() > 0 ? request.getPageNo() - 1 : 0;
        Pageable pageable = PaginationUtils.createPageable(
                pageNo, request.getPageSize(), request.getSortBy(), request.getSortDirection()
        );

        Page<ActivityLog> activityLogPage = activityLogRepository.searchActivityLogs(
                request.getUserId(),
                request.getClientIp(),
                request.getStartDate(),
                request.getEndDate(),
                request.getSearch(),
                pageable
        );

        return paginationMapper.toPaginationResponse(
                activityLogPage,
                activityLogs -> activityLogs.stream()
                        .map(activityLogMapper::toResponse)
                        .toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityLogResponse getActivityLogById(UUID logId) {
        log.info("Getting activity log by ID: {}", logId);
        
        ActivityLog activityLog = activityLogRepository.findByIdAndIsDeletedFalse(logId)
                .orElseThrow(() -> new RuntimeException("Activity log not found"));
        
        return activityLogMapper.toResponse(activityLog);
    }
}
