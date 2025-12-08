package com.emenu.features.product.repository;

import com.emenu.features.product.models.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, UUID> {

    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.product.id = :productId AND pa.isDeleted = false")
    List<ProductAttribute> findByProductId(@Param("productId") UUID productId);
}
