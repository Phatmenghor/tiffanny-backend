package com.emenu.features.product.repository;

import com.emenu.features.product.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID>,
                                              JpaSpecificationExecutor<ProductVariant> {

    // Logic moved to ProductVariantSpecification
}
