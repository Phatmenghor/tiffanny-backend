package com.emenu.features.product.repository;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("SELECT c FROM Category c WHERE c.isDeleted = false")
    List<Category> findAllActive();

    @Query("SELECT c FROM Category c WHERE c.status = :status AND c.isDeleted = false")
    List<Category> findByStatus(@Param("status") Status status);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.isDeleted = false")
    Optional<Category> findByIdAndNotDeleted(@Param("id") UUID id);
}
