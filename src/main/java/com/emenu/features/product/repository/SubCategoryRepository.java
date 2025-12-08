package com.emenu.features.product.repository;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

    @Query("SELECT sc FROM SubCategory sc WHERE sc.isDeleted = false")
    List<SubCategory> findAllActive();

    @Query("SELECT sc FROM SubCategory sc WHERE sc.category.id = :categoryId AND sc.isDeleted = false")
    List<SubCategory> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT sc FROM SubCategory sc WHERE sc.status = :status AND sc.isDeleted = false")
    List<SubCategory> findByStatus(@Param("status") Status status);

    @Query("SELECT sc FROM SubCategory sc WHERE sc.id = :id AND sc.isDeleted = false")
    Optional<SubCategory> findByIdAndNotDeleted(@Param("id") UUID id);
}
