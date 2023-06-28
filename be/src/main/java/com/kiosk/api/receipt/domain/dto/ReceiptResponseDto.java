package com.kiosk.api.receipt.domain.dto;

import com.kiosk.api.order.domain.dto.OrderProductResponseDto;
import com.kiosk.api.order.domain.dto.OrdersResponseDto;
import com.kiosk.api.payment.domain.dto.PaymentResponseDto;
import com.kiosk.api.receipt.domain.entity.Receipt;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class ReceiptResponseDto {

    // TODO: 일단 API에 맞게 개발했으나, orders 관련 내용은 orders로 묶으면 좋을 것 같음. 프론트랑 논의 필요.
    private Long orderId;
    private Long orderNumber;

    private List<OrderProductResponseDto> orderProducts;
    private PaymentResponseDto payment;
    private String orderDateTime;


    public static ReceiptResponseDto from(Receipt receipt) {
        List<OrderProductResponseDto> orderProducts = receipt.getOrderProducts().stream()
                .map(OrderProductResponseDto::from)
                .collect(Collectors.toList());
        PaymentResponseDto payment = PaymentResponseDto.from(receipt.getPayment());
        OrdersResponseDto orders = OrdersResponseDto.from(receipt.getOrders());
        return ReceiptResponseDto.builder()
                .orderId(receipt.getOrderId())
                .orderNumber(orders.getOrderNumber())
                .orderProducts(orderProducts)
                .payment(payment)
                .orderDateTime(orders.getOrderDateTime())
                .build();
    }
}
