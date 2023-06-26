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
    private Long id;
    private Long orderId;
    private Long totalAmount;
    private Long receivedAmount;
    private Long change;
    private PaymentMethod paymentMethod;
}
