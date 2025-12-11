package com.emenu.features.order.specification;

import com.emenu.features.order.enums.OrderStatus;
import com.emenu.features.order.enums.PaymentStatus;
import com.emenu.features.order.models.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> filterOrders(
            String searchTerm,
            OrderStatus status,
            PaymentStatus paymentStatus,
            LocalDateTime createdFrom,
            LocalDateTime createdTo) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always exclude soft-deleted records
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));

            // Search by order number, customer name, or phone number
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm.toLowerCase() + "%";
                Predicate orderNumberPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("orderNumber")), searchPattern
                );
                Predicate customerNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("customerName")), searchPattern
                );
                Predicate phoneNumberPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("phoneNumber")), searchPattern
                );
                predicates.add(criteriaBuilder.or(
                    orderNumberPredicate, 
                    customerNamePredicate, 
                    phoneNumberPredicate
                ));
            }

            // Filter by order status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Filter by payment status
            if (paymentStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
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
