package com.kiosk.api.order.domain.dto;

import com.kiosk.api.order.domain.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderProductResponseDto {
    private Long orderProductId;
    private Integer orderProductCnt;
    private Long productId;


    // toEntity
     public OrderProduct toEntity() {
            return OrderProduct.builder()
                    .orderProductId(orderProductId)
                    .cnt(orderProductCnt)
                    .productId(productId)
                    .build();
     }
}
