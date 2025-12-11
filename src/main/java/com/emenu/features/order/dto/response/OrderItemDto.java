package com.emenu.features.order.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDto {
    private UUID id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
}
