package com.emenu.features.dashboard.controller;

import com.emenu.features.dashboard.dto.DashboardStatsResponse;
import com.emenu.features.dashboard.service.DashboardService;
import com.emenu.shared.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<DashboardStatsResponse>> getDashboardStats() {
        DashboardStatsResponse stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(
                ApiResponse.<DashboardStatsResponse>builder()
                        .status(String.valueOf(200))
                        .message("Dashboard stats fetched successfully")
                        .data(stats)
                        .build()
        );
    }
}
