package com.emenu.features.product.models;

import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_attributes", indexes = {
        @Index(name = "idx_product_attr_product_id", columnList = "product_id"),
        @Index(name = "idx_product_attr_deleted", columnList = "is_deleted")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttribute extends BaseUUIDEntity {

    @Column(name = "attribute_name", nullable = false, length = 255)
    private String attributeName;

    @Column(name = "attribute_value", nullable = false, length = 500)
    private String attributeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
