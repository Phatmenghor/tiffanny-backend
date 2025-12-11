package com.emenu.features.order.dto;

import com.emenu.features.order.enums.OrderStatus;
import com.emenu.features.order.enums.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    @NotNull(message = "Status is required")
    private OrderStatus status;

    @Valid
    @NotEmpty(message = "Order items are required")
    private List<OrderItemRequest> items;
}
