package com.kiosk.api.payment.controller;

import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCardInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCashInDto;
import com.kiosk.api.payment.domain.dto.PaymentResponseDto;
import com.kiosk.api.payment.domain.dto.PaymentResultResponseDto;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final OrderProductRepository orderProductRepository;

    public PaymentController(final PaymentRepository paymentRepository,
        final OrderProductRepository orderProductRepository) {
        this.paymentRepository = paymentRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @PostMapping("/api/payment/cash")
    public PaymentResultResponseDto payByCash(@RequestBody final PayByCashInDto payByCashInDto, Long fail) {
        if (Objects.equals(fail, 400L)) {
            return handle400();
        }
        if (Objects.equals(fail, 500L)) {
            return handle500();
        }

        // TODO: 주문 만들기
        Long orderId = 1L;

        // orderId로 orderProducts 완성해서 모두 저장하기
        List<CartInDto> orderProducts = payByCashInDto.getOrderProducts();
        saveOrderProductsWithOrderId(orderId, orderProducts);

        // 총액이랑 받은 돈으로 거스름 돈 계산해서 payment 저장하기
        Payment payment = payByCashInDto.toEntity(orderId);
        paymentRepository.save(payment);

        // TODO: 영수증 저장하기
        return handle200(orderId, "현금 결제 성공하였습니다.");
    }

    @PostMapping("/api/payment/card")
    public PaymentResultResponseDto payByCard(@RequestBody final PayByCardInDto payByCardInDto, Long fail) {
        if (Objects.equals(fail, 400L)) {
            return handle400();
        }
        if (Objects.equals(fail, 500L)) {
            return handle500();
        }

        // TODO: 주문 만들기
        Long orderId = 1L;

        // orderId로 orderProducts 완성해서 모두 저장하기
        List<CartInDto> orderProducts = payByCardInDto.getOrderProducts();
        saveOrderProductsWithOrderId(orderId, orderProducts);

        // TODO: 3 ~ 7초 휴식, 카드 결제 실패 정하기
        Payment payment = payByCardInDto.toEntity(orderId);
        paymentRepository.save(payment);

        // TODO: 영수증 저장하기

        return handle200(orderId, "카드 결제 성공하였습니다.");
    }

    private void saveOrderProductsWithOrderId(final Long orderId, final List<CartInDto> orderProducts) {
        for (CartInDto orderProduct : orderProducts) {
            OrderProduct completedOrderProduct = orderProduct.toEntity(orderId);
            orderProductRepository.save(completedOrderProduct);
        }
    }

    private PaymentResultResponseDto handle200(final Long orderId, final String message) {
        Map<String, Object> data = Map.of("orderId", orderId);
        Map<String, Object> errorCode = new HashMap<>();
        errorCode.put("status", 200);
        errorCode.put("code", "SUCCESS");
        errorCode.put("message", message);
        return new PaymentResultResponseDto(true, data, errorCode);
    }

    private PaymentResultResponseDto handle400() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> errorCode = new HashMap<>();
        errorCode.put("status", 400);
        errorCode.put("code", "PaymentError");
        errorCode.put("message", "결제가 실패했습니다. 잠시후에 시도해주세요.");
        return new PaymentResultResponseDto(false, data, errorCode);
    }

    private PaymentResultResponseDto handle500() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> errorCode = new HashMap<>();
        errorCode.put("status", 500);
        errorCode.put("code", "ServerError");
        errorCode.put("message", "서버 에러입니다. 잠시 후에 이용해주세요.");
        return new PaymentResultResponseDto(false, data, errorCode);
    }


}
