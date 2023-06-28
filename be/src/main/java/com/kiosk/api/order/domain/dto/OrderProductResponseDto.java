package com.kiosk.api.order.domain.dto;

import com.kiosk.api.order.domain.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class OrderProductResponseDto {
    private String name;
    private Integer amount;
    private String size;
    private String temperature;

    public static OrderProductResponseDto from(OrderProduct orderProduct) {
        return OrderProductResponseDto.builder()
                .name(orderProduct.getName())
                .amount(orderProduct.getAmount())
                .size(orderProduct.getSize())
                .temperature(orderProduct.getTemperature())
                .build();
    }
}
