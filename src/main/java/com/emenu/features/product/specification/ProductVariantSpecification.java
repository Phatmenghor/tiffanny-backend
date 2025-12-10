package com.emenu.features.product.specification;

import com.emenu.features.product.models.ProductVariant;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductVariantSpecification {

    public static Specification<ProductVariant> hasProductId(UUID productId) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("product").get("id"), productId);
    }

    public static Specification<ProductVariant> isNotDeleted() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.isFalse(root.get("isDeleted"));
    }
}
