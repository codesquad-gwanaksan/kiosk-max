package com.kiosk.api.product.web.controller.dto;

import com.kiosk.api.product.domain.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderProductResponseDto {
    private Long orderProductId;
    private Long orderProductCnt;
    private Long productId;


    // toEntity
     public OrderProduct toEntity() {
            return OrderProduct.builder()
                    .orderProductId(orderProductId)
                    .orderProductCnt(orderProductCnt)
                    .productId(productId)
                    .build();
     }
}
