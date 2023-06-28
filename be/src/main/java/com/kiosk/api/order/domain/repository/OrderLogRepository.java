package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderLog;

import java.util.List;
import java.util.Map;

public interface OrderLogRepository {
    Map<Long, Long> saveAll(List<OrderLog> orderLogs);
}
