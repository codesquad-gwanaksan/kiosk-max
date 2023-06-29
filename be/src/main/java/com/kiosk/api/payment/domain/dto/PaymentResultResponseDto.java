package com.kiosk.api.payment.domain.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PaymentResultResponseDto {
    private boolean success;
    private Map<String, Object> data;
    private Map<String, Object> errorCode;
}
