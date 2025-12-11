package com.emenu.features.order.controller;

import com.emenu.features.order.dto.request.AllOrderRequest;
import com.emenu.features.order.dto.request.CreateOrderRequest;
import com.emenu.features.order.dto.request.UpdateOrderStatusRequest;
import com.emenu.features.order.dto.response.AllOrderResponseDto;
import com.emenu.features.order.dto.response.OrderDto;
import com.emenu.features.order.service.OrderService;
import com.emenu.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerName());
        OrderDto response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable UUID id) {
        log.info("Fetching order by ID: {}", id);
        OrderDto response = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", response));
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderByOrderNumber(@PathVariable String orderNumber) {
        log.info("Fetching order by number: {}", orderNumber);
        OrderDto response = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", response));
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponse<AllOrderResponseDto>> getAllOrders(@RequestBody AllOrderRequest request) {
        log.info("Fetching all orders");
        AllOrderResponseDto response = orderService.getAllOrders(request);
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", response));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        log.info("Updating order status for ID: {}", id);
        OrderDto response = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable UUID id) {
        log.info("Deleting order: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted successfully", null));
    }
}
