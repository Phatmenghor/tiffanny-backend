package com.emenu.features.product.models;

import com.emenu.enums.common.Status;
import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_promotions", indexes = {
        @Index(name = "idx_product_promo_status", columnList = "status"),
        @Index(name = "idx_product_promo_deleted", columnList = "is_deleted"),
        @Index(name = "idx_product_promo_product_id", columnList = "product_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductPromotion extends BaseUUIDEntity {

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_view", nullable = false)
    private Long productView = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;
}
