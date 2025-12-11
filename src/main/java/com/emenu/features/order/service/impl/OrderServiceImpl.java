package com.emenu.features.order.service.impl;

import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.order.dto.request.AllOrderRequest;
import com.emenu.features.order.dto.request.CreateOrderRequest;
import com.emenu.features.order.dto.request.UpdateOrderStatusRequest;
import com.emenu.features.order.dto.response.AllOrderResponseDto;
import com.emenu.features.order.dto.response.OrderDto;
import com.emenu.features.order.enums.OrderStatus;
import com.emenu.features.order.enums.PaymentStatus;
import com.emenu.features.order.mapper.OrderMapper;
import com.emenu.features.order.models.Order;
import com.emenu.features.order.models.OrderItem;
import com.emenu.features.order.repository.OrderRepository;
import com.emenu.features.order.service.OrderService;
import com.emenu.features.order.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        log.info("Creating new order for customer: {}", request.getCustomerName());

        Order order = orderMapper.toEntity(request);
        
        // Generate order number
        String orderNumber = generateOrderNumber();
        order.setOrderNumber(orderNumber);

        // Map items
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            request.getItems().forEach(itemReq -> {
                OrderItem item = orderMapper.toOrderItem(itemReq, order);
                order.getItems().add(item);
            });
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order created with number: {}", orderNumber);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(UUID id) {
        log.info("Fetching order by ID: {}", id);
        Order order = orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderByOrderNumber(String orderNumber) {
        log.info("Fetching order by number: {}", orderNumber);
        Order order = orderRepository.findByOrderNumberAndIsDeletedFalse(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order not found with number: " + orderNumber));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public AllOrderResponseDto getAllOrders(AllOrderRequest request) {
        log.info("Fetching all orders with filters");
        Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize(), 
                Sort.by(Sort.Direction.DESC, "createdAt"));

        var spec = OrderSpecification.filterOrders(
                request.getSearch(),
                request.getStatus(),
                request.getPaymentStatus(),
                null,  // createdFrom
                null   // createdTo
        );

        Page<Order> page = orderRepository.findAll(spec, pageable);

        List<OrderDto> content = page.stream()
                .map(orderMapper::toDto)
                .toList();

        return orderMapper.mapToListDto(content, page);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(UUID id, UpdateOrderStatusRequest request) {
        log.info("Updating order status for ID: {}", id);
        Order order = orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getPaymentStatus() != null) {
            order.setPaymentStatus(request.getPaymentStatus());
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(UUID id) {
        log.info("Deleting order: {}", id);
        Order order = orderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));

        order.softDelete();
        orderRepository.save(order);
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "ORD-" + timestamp + "-" + randomPart;
    }
}
