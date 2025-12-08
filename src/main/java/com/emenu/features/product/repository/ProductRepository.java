package com.emenu.features.product.repository;

import com.emenu.enums.common.Status;
import com.emenu.features.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
    List<Product> findAllActive();

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.isDeleted = false")
    List<Product> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT p FROM Product p WHERE p.subCategory.id = :subCategoryId AND p.isDeleted = false")
    List<Product> findBySubCategoryId(@Param("subCategoryId") UUID subCategoryId);

    @Query("SELECT p FROM Product p WHERE p.status = :status AND p.isDeleted = false")
    List<Product> findByStatus(@Param("status") Status status);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isDeleted = false")
    Optional<Product> findByIdAndNotDeleted(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE Product p SET p.productView = p.productView + 1 WHERE p.id = :id")
    void incrementProductView(@Param("id") UUID id);
}
