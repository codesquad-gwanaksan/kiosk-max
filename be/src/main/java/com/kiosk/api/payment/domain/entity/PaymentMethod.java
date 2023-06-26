package com.kiosk.api.payment.domain.entity;

public enum PaymentMethod {
    CARD, CASH;

    public static PaymentMethod from(String value) {
        for (PaymentMethod paymentMethod : values()) {
            if (paymentMethod.name().equalsIgnoreCase(value)) {
                return paymentMethod;
            }
        }

        throw new RuntimeException("CARD, CASH 중 어느 값도 찾을 수 없습니다. value: " + value);
    }
}
