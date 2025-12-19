package com.emenu.features.dashboard.service.impl;

import com.emenu.features.auth.repository.ActivityLogRepository;
import com.emenu.features.auth.repository.UserRepository;
import com.emenu.features.dashboard.dto.DashboardStatsResponse;
import com.emenu.features.dashboard.service.DashboardService;
import com.emenu.features.order.enums.OrderStatus;
import com.emenu.features.order.repository.OrderRepository;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.repository.CategoryRepository;
import com.emenu.features.product.repository.ProductRepository;
import com.emenu.features.product.repository.SubCategoryRepository;
import com.emenu.features.product.specification.ProductSpecification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ActivityLogRepository activityLogRepository;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalProducts = productRepository.count();
        long totalCategories = categoryRepository.count();
        long totalSubCategories = subCategoryRepository.count();

        // Total Products on Promotion
        // We replicate the logic from hasDiscount specification if possible, or just use Specification directly.
        // It's cleaner to construct the Specification manually here since we want a count.
        
        Specification<Product> hasDiscountSpec = (root, query, cb) -> {
            Join<Object, Object> variants = root.join("variants", JoinType.RIGHT);
            List<Predicate> predicates = new ArrayList<>();
            
            // Check discount > 0
            predicates.add(cb.greaterThan(variants.get("discount"), 0));
            // Check start date <= now OR null
            predicates.add(cb.or(
                cb.isNull(variants.get("discountStartDate")),
                cb.lessThanOrEqualTo(variants.get("discountStartDate"), LocalDateTime.now())
            ));
            // Check end date >= now OR null
            predicates.add(cb.or(
                cb.isNull(variants.get("discountEndDate")),
                cb.greaterThanOrEqualTo(variants.get("discountEndDate"), LocalDateTime.now())
            ));
            
            query.distinct(true); 
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        long totalProductsOnPromotion = productRepository.count(hasDiscountSpec);

        long totalOrdersProcessing = orderRepository.countByStatus(OrderStatus.PROCESS);
        long totalCompletedOrders = orderRepository.countByStatus(OrderStatus.SUCCESS);
        long totalFailedOrders = orderRepository.countByStatus(OrderStatus.FAIL);

        // App Viewers Today
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        long totalAppViewersToday = activityLogRepository.countDistinctUsersActiveBetween(startOfDay, endOfDay);
        
        return DashboardStatsResponse.builder()
                .totalUsers(totalUsers)
                .totalProducts(totalProducts)
                .totalProductsOnPromotion(totalProductsOnPromotion)
                .totalOrdersProcessing(totalOrdersProcessing)
                .totalCompletedOrders(totalCompletedOrders)
                .totalFailedOrders(totalFailedOrders)
                .totalAppViewersToday(totalAppViewersToday)
                .totalCategories(totalCategories)
                .totalSubCategories(totalSubCategories)
                .build();
    }
}
