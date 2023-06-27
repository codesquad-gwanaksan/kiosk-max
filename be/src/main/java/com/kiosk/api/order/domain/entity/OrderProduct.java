package com.kiosk.api.order.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProduct {
    private Long orderProductId;
    private Long orderId;
    private Long productId;

    private String name;
    private Integer cnt;
    private String size;
    private String temperature;
}
