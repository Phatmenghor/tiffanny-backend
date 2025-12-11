package com.emenu.features.order.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AllOrderResponseDto {
    private List<OrderDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
