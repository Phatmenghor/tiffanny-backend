package com.emenu.features.auth.specification;

import com.emenu.enums.user.AccountStatus;
import com.emenu.features.auth.models.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterUsers(
            String searchTerm,
            AccountStatus status,
            String role,
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            LocalDateTime lastLoginFrom,
            LocalDateTime lastLoginTo) {
        
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude soft-deleted records
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));

            // Search by user identifier, first name, last name, or email
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm.toLowerCase() + "%";
                Predicate userIdentifierPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("userIdentifier")), searchPattern
                );
                Predicate firstNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("firstName")), searchPattern
                );
                Predicate lastNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("lastName")), searchPattern
                );
                Predicate emailPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")), searchPattern
                );
                predicates.add(criteriaBuilder.or(
                    userIdentifierPredicate, firstNamePredicate, lastNamePredicate, emailPredicate
                ));
            }

            // Filter by account status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("accountStatus"), status));
            }

            // Filter by role (contains check for roles collection)
            if (role != null && !role.trim().isEmpty()) {
                predicates.add(criteriaBuilder.isMember(role, root.get("roles")));
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

            // Filter by last login date range
            if (lastLoginFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("lastLogin"), lastLoginFrom
                ));
            }

            if (lastLoginTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("lastLogin"), lastLoginTo
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
