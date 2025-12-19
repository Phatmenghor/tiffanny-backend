package com.emenu.features.product.models;

import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_suggestions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSuggestion extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggested_product_id", nullable = false)
    private Product suggestedProduct;
}
