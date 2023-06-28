package com.kiosk.api.payment.domain.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResultResponseDto {
    private boolean success;
    private Map<String, Object> data;
    private Map<String, Object> errorCode;
}
