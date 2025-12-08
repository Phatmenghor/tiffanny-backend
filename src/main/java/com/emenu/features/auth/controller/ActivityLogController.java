package com.emenu.features.auth.controller;

import com.emenu.features.auth.dto.filter.ActivityLogFilterRequest;
import com.emenu.features.auth.dto.response.ActivityLogResponse;
import com.emenu.features.auth.service.ActivityLogService;
import com.emenu.shared.dto.ApiResponse;
import com.emenu.shared.dto.PaginationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/activity-logs")
@RequiredArgsConstructor
@Slf4j
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<PaginationResponse<ActivityLogResponse>>> getActivityLogs(
            @Valid @RequestBody ActivityLogFilterRequest request) {
        log.info("Get all activity logs");
        PaginationResponse<ActivityLogResponse> response = activityLogService.getActivityLogs(request);
        return ResponseEntity.ok(ApiResponse.success("Activity logs retrieved", response));
    }

    @GetMapping("/{logId}")
    public ResponseEntity<ApiResponse<ActivityLogResponse>> getActivityLogById(@PathVariable UUID logId) {
        log.info("Get activity log: {}", logId);
        ActivityLogResponse response = activityLogService.getActivityLogById(logId);
        return ResponseEntity.ok(ApiResponse.success("Activity log retrieved", response));
    }
}
