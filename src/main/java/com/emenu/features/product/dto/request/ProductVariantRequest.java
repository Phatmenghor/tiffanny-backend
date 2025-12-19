package com.emenu.features.product.dto.request;

import com.emenu.enums.product.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductVariantRequest {

    private String name;

    private BigDecimal price;

    private Integer stock;

    private BigDecimal discount;

    private DiscountType discountType;

    private LocalDate discountStartDate;

    private LocalDate discountEndDate;

    private String imageCover;
}
