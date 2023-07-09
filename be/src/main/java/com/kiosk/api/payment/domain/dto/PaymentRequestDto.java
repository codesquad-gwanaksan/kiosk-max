package com.kiosk.api.payment.domain.dto;

import static com.kiosk.api.payment.domain.entity.PaymentMethod.CARD;
import static com.kiosk.api.payment.domain.entity.PaymentMethod.CASH;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.payment.domain.entity.Payment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

public class PaymentRequestDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PayByCashInDto {

        @NonNull
        private List<CartInDto> orderProducts;
        @NonNull
        private Integer totalPrice;
        @NonNull
        private Integer receivedPrice;

        public Payment toEntity(Long orderId) {
            return Payment.builder()
                .orderId(orderId)
                .totalPrice(this.totalPrice)
                .receivedPrice(this.receivedPrice)
                .remainedPrice(calculateChange(this.totalPrice, this.receivedPrice))
                .method(CASH)
                .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PayByCardInDto {

        private List<CartInDto> orderProducts;
        private Integer totalPrice;

        public Payment toEntity(Long orderId) {
            final int ZERO = 0;

            return Payment.builder()
                .orderId(orderId)
                .totalPrice(this.totalPrice)
                .receivedPrice(this.totalPrice)
                .remainedPrice(ZERO)
                .method(CARD)
                .build();
        }
    }

    private static Integer calculateChange(final Integer totalPrice, final Integer receivedPrice) {
        if (totalPrice > receivedPrice) {
            throw new RuntimeException("받은 금액보다 총액이 더 큽니다.");
        }

        return receivedPrice - totalPrice;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CartInDto {

        private Long productId;
        private String name;
        private Integer amount;
        private String size;
        private String temperature;

        public OrderProduct toEntity(Long orderId) {
            return OrderProduct.builder()
                .orderId(orderId)
                .amount(this.amount)
                .productId(this.productId)
                .name(this.name)
                .size(this.size)
                .temperature(this.temperature)
                .build();
        }
    }
}
