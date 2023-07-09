package com.kiosk.api.receipt.controller;

import com.kiosk.api.receipt.domain.dto.ReceiptResponseDto;
import com.kiosk.api.receipt.domain.entity.Receipt;
import com.kiosk.api.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping("/api/receipt")
    public ReceiptResponseDto getReceiptInformation(@RequestParam Long orderId) {
        // TODO: orderId가 유효한지 확인
        Receipt receipt = receiptService.getReceiptInformation(orderId);
        return ReceiptResponseDto.from(receipt);
    }
}
