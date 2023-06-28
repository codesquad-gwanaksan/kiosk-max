package com.kiosk.api.payment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Payment {
    private Long paymentId;
    private Long orderId;
    private Integer totalPrice;
    private Integer receivedPrice;
    private Integer remainedPrice;
    private PaymentMethod paymentMethod;
}
