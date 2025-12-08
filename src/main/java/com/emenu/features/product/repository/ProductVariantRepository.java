package com.emenu.features.product.repository;

import com.emenu.features.product.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    @Query("SELECT pv FROM ProductVariant pv WHERE pv.product.id = :productId AND pv.isDeleted = false")
    List<ProductVariant> findByProductId(@Param("productId") UUID productId);
}
