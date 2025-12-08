package com.emenu.features.product.models;

import com.emenu.enums.common.Status;
import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sub_categories", indexes = {
        @Index(name = "idx_subcategory_status", columnList = "status"),
        @Index(name = "idx_subcategory_deleted", columnList = "is_deleted"),
        @Index(name = "idx_subcategory_category_id", columnList = "category_id"),
        @Index(name = "idx_subcategory_name", columnList = "name")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory extends BaseUUIDEntity {

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
