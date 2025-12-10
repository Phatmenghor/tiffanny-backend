package com.emenu.features.product.repository;

import com.emenu.features.product.models.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, UUID>,
                                                JpaSpecificationExecutor<ProductAttribute> {

    // Logic moved to ProductAttributeSpecification
}
