package com.kiosk.api.product.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProduct {
    private Long orderProductCnt;
    private String productSize;
    private String productTemperature;
    private Long orderProductId;
    private Long orderId;
    private Long productId;
}
