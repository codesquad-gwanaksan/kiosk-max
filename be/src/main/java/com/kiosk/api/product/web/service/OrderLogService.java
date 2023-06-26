package com.kiosk.api.product.web.service;

import com.kiosk.api.product.domain.repository.OrderLogRepository;
import com.kiosk.api.product.domain.repository.OrderProductRepository;
import com.kiosk.api.product.web.controller.dto.OrderProductResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderLogService {
}

