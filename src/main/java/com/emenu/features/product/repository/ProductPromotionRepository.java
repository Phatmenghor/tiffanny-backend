package com.emenu.features.product.repository;

import com.emenu.features.product.models.ProductPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, UUID>,
                                                     JpaSpecificationExecutor<ProductPromotion> {

    // Use specification-based queries instead of hardcoded @Query
    // All filtering logic is now in ProductPromotionSpecification
    
    Optional<ProductPromotion> findByIdAndIsDeletedFalse(UUID id);
    
    Optional<ProductPromotion> findByProductIdAndIsDeletedFalse(UUID productId);
}
