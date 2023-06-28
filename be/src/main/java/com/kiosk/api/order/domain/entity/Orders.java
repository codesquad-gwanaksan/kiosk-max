package com.kiosk.api.order.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Orders {
    private Long orderId;
    private Long orderNumber;
    private String orderDateTime;
}
