package com.emenu.features.product.specification;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.Category;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategorySpec {

    public static Specification<Category> hasStatus(Status status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Category> searchByName(String search) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }

            predicates.add(cb.isFalse(root.get("isDeleted")));

            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("status")), likePattern),
                    cb.like(cb.lower(root.get("createdAt")), likePattern)
            );
        };
    }
}
