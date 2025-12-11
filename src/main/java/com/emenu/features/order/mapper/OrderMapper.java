package com.emenu.features.order.mapper;

import com.emenu.features.order.dto.request.CreateOrderRequest;
import com.emenu.features.order.dto.request.OrderItemRequest;
import com.emenu.features.order.dto.response.AllOrderResponseDto;
import com.emenu.features.order.dto.response.OrderDto;
import com.emenu.features.order.dto.response.OrderItemDto;
import com.emenu.features.order.models.Order;
import com.emenu.features.order.models.OrderItem;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "items", expression = "java(mapOrderItems(order))")
    OrderDto toDto(Order order);

    OrderItemDto toItemDto(OrderItem orderItem);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    Order toEntity(CreateOrderRequest request);

    default List<OrderItemDto> mapOrderItems(Order order) {
        if (order == null || order.getItems() == null) {
            return List.of();
        }
        return order.getItems().stream()
                .filter(item -> Boolean.FALSE.equals(item.getIsDeleted()))
                .map(this::toItemDto)
                .toList();
    }

    default OrderItem toOrderItem(OrderItemRequest request, Order order) {
        OrderItem item = new OrderItem();
        item.setProductName(request.getProductName());
        item.setQuantity(request.getQuantity());
        item.setPrice(request.getPrice());
        item.setTotal(request.getTotal());
        item.setOrder(order);
        return item;
    }

    default AllOrderResponseDto mapToListDto(List<OrderDto> content, Page<Order> page) {
        AllOrderResponseDto response = new AllOrderResponseDto();
        response.setContent(content);
        response.setPageNo(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }
}
