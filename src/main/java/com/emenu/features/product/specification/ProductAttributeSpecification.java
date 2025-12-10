package com.emenu.features.product.specification;

import com.emenu.features.product.models.ProductAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ProductAttributeSpecification {

    public static Specification<ProductAttribute> hasProductId(UUID productId) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("product").get("id"), productId);
    }

    public static Specification<ProductAttribute> isNotDeleted() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.isFalse(root.get("isDeleted"));
    }
}
