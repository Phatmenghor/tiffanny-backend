package com.emenu.features.order.service;

import com.emenu.features.order.dto.request.AllOrderRequest;
import com.emenu.features.order.dto.request.CreateOrderRequest;
import com.emenu.features.order.dto.request.UpdateOrderStatusRequest;
import com.emenu.features.order.dto.response.AllOrderResponseDto;
import com.emenu.features.order.dto.response.OrderDto;

import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequest request);
    OrderDto getOrderById(UUID id);
    OrderDto getOrderByOrderNumber(String orderNumber);
    AllOrderResponseDto getAllOrders(AllOrderRequest request);
    OrderDto updateOrderStatus(UUID id, UpdateOrderStatusRequest request);
    void deleteOrder(UUID id);
}
