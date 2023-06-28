package com.kiosk.api.payment.controller;

import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCardInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCashInDto;
import com.kiosk.api.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/api/payment/cash")
    public Map<String, Long> payByCash(@RequestBody final PayByCashInDto payByCashInDto) {
        return paymentService.createPaymentByCash(payByCashInDto);
    }

    @PostMapping("/api/payment/card")
    public Map<String, Long> payByCard(@RequestBody final PayByCardInDto payByCardInDto) {
        return paymentService.createPaymentByCard(payByCardInDto);
    }
}
