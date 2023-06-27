package com.kiosk.api.payment.controller;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.CartInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCardInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCashInDto;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Transactional
@RestController
public class PaymentController {
    private final PaymentRepository paymentRepository;
    private final OrderProductRepository orderProductRepository;

    public PaymentController(final PaymentRepository paymentRepository, final OrderProductRepository orderProductRepository) {
        this.paymentRepository = paymentRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @PostMapping("/api/payment/cash")
    public Payment payByCash(@RequestBody final PayByCashInDto payByCashInDto) {
        // TODO: 주문 만들기
        Long orderId = 0L;

        // orderId로 orderProducts 완성해서 모두 저장하기
        List<CartInDto> orderProducts = payByCashInDto.getOrderProducts();
        saveOrderProductsWithOrderId(orderId, orderProducts);

        // 총액이랑 받은 돈으로 거스름 돈 계산해서 payment 저장하기
        Payment payment = payByCashInDto.toEntity(orderId);
        paymentRepository.save(payment);

        // TODO: 영수증 저장하기

        return payment;
    }

    @PostMapping("/api/payment/card")
    public Payment payByCard(@RequestBody final PayByCardInDto payByCardInDto) {
        // TODO: 주문 만들기
        Long orderId = 0L;

        // orderId로 orderProducts 완성해서 모두 저장하기
        List<CartInDto> orderProducts = payByCardInDto.getOrderProducts();
        saveOrderProductsWithOrderId(orderId, orderProducts);

        // TODO: 3 ~ 7초 휴식, 카드 결제 실패 정하기
        Payment payment = payByCardInDto.toEntity(orderId);
        paymentRepository.save(payment);

        // TODO: 영수증 저장하기

        return payment;
    }

    private void saveOrderProductsWithOrderId(final Long orderId, final List<CartInDto> orderProducts) {
        for (CartInDto orderProduct : orderProducts) {
            OrderProduct completedOrderProduct = orderProduct.toEntity(orderId);
            orderProductRepository.save(completedOrderProduct);
        }
    }
}
