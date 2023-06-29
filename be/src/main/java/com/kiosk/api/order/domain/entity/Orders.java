package com.kiosk.api.order.domain.entity;

import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Orders {

    private Long orderId;
    private Long orderNumber;
    private String orderDateTime;

}
