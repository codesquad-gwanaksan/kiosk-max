package com.kiosk.api.product.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProduct {
    private Long orderProductCnt;
    private String ProductSize;
    private String ProductTemperature;
    private Long orderProductId;
    private Long orderId;
    private Long productId;
}
