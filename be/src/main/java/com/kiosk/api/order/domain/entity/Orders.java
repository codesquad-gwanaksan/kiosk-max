package com.kiosk.api.order.domain.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Orders {

    private Long orderId;
    private Long orderNumber;
    private String orderDateTime;

}
