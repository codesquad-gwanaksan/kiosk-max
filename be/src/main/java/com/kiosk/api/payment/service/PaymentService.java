package com.kiosk.api.payment.service;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCashInDto;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderProductRepository orderProductRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Map<String, Long> createPaymentByCash(final PayByCashInDto payByCashInDto) {
        // TODO: 주문 만들기
        Long orderId = 0L;

        // orderId로 orderProducts 완성해서 모두 저장하기
        List<PaymentRequestDto.CartInDto> orderProducts = payByCashInDto.getOrderProducts();
        saveOrderProductsWithOrderId(orderId, orderProducts);

        // 총액이랑 받은 돈으로 거스름 돈 계산해서 payment 저장하기
        Payment payment = payByCashInDto.toEntity(orderId);
        paymentRepository.save(payment);

        // TODO: 무늬만 DTO -> 나중에 공용 ResponseDTO 에 넣기
        Map<String, Long> payByCashOutDto = Map.of("orderId", orderId);
        return payByCashOutDto;
    }

    @Transactional
    public Map<String, Long> createPaymentByCard(final PaymentRequestDto.PayByCardInDto payByCardInDto) {
        // TODO: 주문 만들기
        Long orderId = 0L;

        // orderId로 orderProducts 완성해서 모두 저장하기
        List<PaymentRequestDto.CartInDto> orderProducts = payByCardInDto.getOrderProducts();
        saveOrderProductsWithOrderId(orderId, orderProducts);

        // TODO: 3 ~ 7초 휴식, 카드 결제 실패 정하기
        Payment payment = payByCardInDto.toEntity(orderId);
        paymentRepository.save(payment);

        // TODO: 무늬만 DTO -> 나중에 공용 ResponseDTO 에 넣기
        Map<String, Long> payByCardOutDto = Map.of("orderId", orderId);
        return payByCardOutDto;
    }

    private void saveOrderProductsWithOrderId(final Long orderId, final List<PaymentRequestDto.CartInDto> orderProducts) {
        for (PaymentRequestDto.CartInDto orderProduct : orderProducts) {
            OrderProduct completedOrderProduct = orderProduct.toEntity(orderId);
            orderProductRepository.save(completedOrderProduct);
        }
    }
}
