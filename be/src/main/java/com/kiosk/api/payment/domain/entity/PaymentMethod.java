package com.kiosk.api.payment.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethod {

    @JsonProperty("card")
    CARD,
    @JsonProperty("cash")
    CASH;

    public static PaymentMethod from(String value) {
        for (PaymentMethod paymentMethod : values()) {
            if (paymentMethod.name().equalsIgnoreCase(value)) {
                return paymentMethod;
            }
        }

        throw new RuntimeException("CARD, CASH 중 어느 값도 찾을 수 없습니다. value: " + value);
    }
}
