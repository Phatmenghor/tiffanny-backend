package com.emenu.features.product.repository;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.ProductPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, UUID> {

    @Query("SELECT pp FROM ProductPromotion pp WHERE pp.isDeleted = false")
    List<ProductPromotion> findAllActive();

    @Query("SELECT pp FROM ProductPromotion pp WHERE pp.product.id = :productId AND pp.isDeleted = false")
    Optional<ProductPromotion> findByProductId(@Param("productId") UUID productId);

    @Query("SELECT pp FROM ProductPromotion pp WHERE pp.status = :status AND pp.isDeleted = false")
    List<ProductPromotion> findByStatus(@Param("status") Status status);

    @Query("SELECT pp FROM ProductPromotion pp WHERE pp.id = :id AND pp.isDeleted = false")
    Optional<ProductPromotion> findByIdAndNotDeleted(@Param("id") UUID id);
}
