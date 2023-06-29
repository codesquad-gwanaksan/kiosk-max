package com.kiosk.api.order.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Orders {

    private Long orderId;
    private Long orderNumber;
    private String orderDatetime;

}
