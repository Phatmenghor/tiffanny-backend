package com.emenu.features.product.repository;

import com.emenu.features.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>,
                                           JpaSpecificationExecutor<Product> {

    // Use specification-based queries instead of hardcoded @Query
    // All filtering logic is now in ProductSpecification
    
    Optional<Product> findByIdAndIsDeletedFalse(UUID id);
    
    // Keep this modifying query as it's not a filter
    @Modifying
    @Query("UPDATE Product p SET p.productView = p.productView + 1 WHERE p.id = :id")
    void incrementProductView(@Param("id") UUID id);
}
