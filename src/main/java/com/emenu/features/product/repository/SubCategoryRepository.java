package com.emenu.features.product.repository;

import com.emenu.features.product.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID>,
                                                JpaSpecificationExecutor<SubCategory> {

    // Use specification-based queries instead of hardcoded @Query
    // All filtering logic is now in SubCategorySpecification
    
    Optional<SubCategory> findByIdAndIsDeletedFalse(UUID id);
}
