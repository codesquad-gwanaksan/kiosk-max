package com.kiosk.api.order.domain.dto;

import com.kiosk.api.order.domain.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class OrdersResponseDto {

    private Long orderId;
    private String orderDateTime;
    private Long orderNumber;

    public static OrdersResponseDto from(Orders orders) {
        return OrdersResponseDto.builder()
                .orderId(orders.getOrderId())
                .orderDateTime(orders.getOrderDateTime())
                .orderNumber(orders.getOrderNumber())
                .build();
    }

}
