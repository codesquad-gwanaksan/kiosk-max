package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderLog;
import com.kiosk.api.order.domain.entity.OrderProduct;

import java.time.LocalDate;
import java.util.List;

public interface OrderProductRepository {

    Long save(OrderProduct orderProduct);

    List<OrderProduct> findAllBy(Long orderId);

    List<OrderLog> findByDate(LocalDate date);
}
