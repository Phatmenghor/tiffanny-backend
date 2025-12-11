package com.emenu.features.order.repository;

import com.emenu.features.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByIdAndIsDeletedFalse(UUID id);
    Optional<Order> findByOrderNumberAndIsDeletedFalse(String orderNumber);
}
