package com.emenu.features.order.dto.request;

import com.emenu.features.order.enums.OrderStatus;
import com.emenu.features.order.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllOrderRequest {
    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = 10;

    private String search;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
}
