package com.emenu.features.product.specification;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.Category;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {

    public static Specification<Category> filterCategories(
            String searchTerm,
            Status status,
            LocalDateTime createdFrom,
            LocalDateTime createdTo) {
        
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude soft-deleted records
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));

            // Search by name (case-insensitive, partial match)
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + searchTerm.toLowerCase() + "%"
                ));
            }

            // Filter by status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
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
