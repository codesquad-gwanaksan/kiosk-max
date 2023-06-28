package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.Orders;

import java.util.Optional;

public interface OrdersRepository {
    Optional<Orders> findBy(Long orderId);
}
