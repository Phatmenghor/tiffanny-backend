package com.emenu.features.order.dto;

import com.emenu.features.order.enums.OrderStatus;
import com.emenu.features.order.enums.PaymentStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private OrderStatus status;
    private PaymentStatus paymentStatus;
}
