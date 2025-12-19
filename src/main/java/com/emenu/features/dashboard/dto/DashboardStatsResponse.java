package com.emenu.features.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsResponse {
    private long totalUsers;
    private long totalProducts;
    private long totalProductsOnPromotion;
    private long totalOrdersProcessing;
    private long totalCompletedOrders;
    private long totalFailedOrders;
    private long totalAppViewersToday;
    private long totalCategories;
    private long totalSubCategories;
}
