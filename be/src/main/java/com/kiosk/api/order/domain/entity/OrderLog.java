package com.kiosk.api.order.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OrderLog {
    private String salesDate;
    private Long categoryId;
    private Long productId;
    private Long salesAmount;
}
