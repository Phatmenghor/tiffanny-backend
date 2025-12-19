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
            LocalDateTime createdTo,
            Boolean hasDiscount) {
        
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

            // Filter by discount (hasDiscount)
            if (Boolean.TRUE.equals(hasDiscount)) {
                // Join with variants to check discounts
                // This assumes Product has a "variants" OneToMany relationship
                var variantsJoin = root.join("variants");
                
                // Discount > 0
                Predicate discountExists = criteriaBuilder.greaterThan(
                    variantsJoin.get("discount"), BigDecimal.ZERO
                );

                // Start Date <= NOW (or null)
                Predicate startDateValid = criteriaBuilder.or(
                    criteriaBuilder.isNull(variantsJoin.get("discountStartDate")),
                    criteriaBuilder.lessThanOrEqualTo(variantsJoin.get("discountStartDate"), LocalDateTime.now())
                );
                
                // End Date >= NOW (or null)
                Predicate endDateValid = criteriaBuilder.or(
                    criteriaBuilder.isNull(variantsJoin.get("discountEndDate")),
                    criteriaBuilder.greaterThanOrEqualTo(variantsJoin.get("discountEndDate"), LocalDateTime.now())
                );
                
                // Variant not deleted (if applicable, assuming variants have isDeleted)
                Predicate variantNotDeleted = criteriaBuilder.isFalse(variantsJoin.get("isDeleted"));

                predicates.add(criteriaBuilder.and(
                    discountExists, 
                    startDateValid, 
                    endDateValid,
                    variantNotDeleted
                ));
                 // Ensure distinct results since we joined a OneToMany
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
