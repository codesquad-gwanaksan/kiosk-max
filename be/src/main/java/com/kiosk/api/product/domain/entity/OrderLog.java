package com.kiosk.api.product.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class OrderLog {
    private Long productId;
    private Long salesCnt;
    private LocalDate salesDate;
}
