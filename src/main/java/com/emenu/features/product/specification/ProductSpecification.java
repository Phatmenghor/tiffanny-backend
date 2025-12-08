package com.emenu.features.product.specification;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductSpecification {

    public static Specification<Product> filterProducts(
            String searchTerm,
            Status status,
            UUID categoryId,
            UUID subCategoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            Long minViews,
            Long maxViews,
            LocalDateTime createdFrom,
            LocalDateTime createdTo) {
        
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude soft-deleted records
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));

            // Search by name or description
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm.toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), searchPattern
                );
                Predicate descPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")), searchPattern
                );
                predicates.add(criteriaBuilder.or(namePredicate, descPredicate));
            }

            // Filter by status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Filter by category
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            // Filter by subcategory
            if (subCategoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("subCategory").get("id"), subCategoryId));
            }

            // Filter by product view range
            if (minViews != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("productView"), minViews
                ));
            }

            if (maxViews != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("productView"), maxViews
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
