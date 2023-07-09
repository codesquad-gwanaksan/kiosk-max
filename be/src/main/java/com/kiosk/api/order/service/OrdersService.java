package com.kiosk.api.order.service;

import com.kiosk.api.order.domain.entity.Orders;
import com.kiosk.api.order.domain.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersService {
    private final OrdersRepository ordersRepository;

    public Integer createOrder() {
        return ordersRepository.save(
                Orders.builder()
                        .orderDatetime(LocalDateTime.now().toString())
                        .build());
    }
}
