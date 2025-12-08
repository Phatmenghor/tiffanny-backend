package com.emenu.features.auth.specification;

import com.emenu.features.auth.models.ActivityLog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivityLogSpecification {

    public static Specification<ActivityLog> filterActivityLogs(
            UUID userId,
            String clientIp,
            String device,
            String location,
            LocalDateTime createdFrom,
            LocalDateTime createdTo) {
        
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude soft-deleted records
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));

            // Filter by user ID
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            // Filter by client IP
            if (clientIp != null && !clientIp.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("clientIp"), clientIp));
            }

            // Filter by device (contains)
            if (device != null && !device.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("device")),
                    "%" + device.toLowerCase() + "%"
                ));
            }

            // Filter by location (contains)
            if (location != null && !location.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("location")),
                    "%" + location.toLowerCase() + "%"
                ));
            }

            // Filter by creation date range
            if (createdFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("createdAt"), createdFrom
                ));
            }

            if (createdTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("createdAt"), createdTo
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
