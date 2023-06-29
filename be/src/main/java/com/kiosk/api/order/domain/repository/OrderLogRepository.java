package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderLogRepository {

    Map<Long, Long> saveAll(List<OrderLog> orderLogs);

    List<OrderLog> findAllByDate(LocalDate localDate);
}
