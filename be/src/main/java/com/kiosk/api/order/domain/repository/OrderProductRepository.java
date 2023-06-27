package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderProduct;
import java.util.List;

public interface OrderProductRepository {

    Long save(OrderProduct orderProduct);
    List<OrderProduct> findAllBy(Long orderId);

}
