package com.kiosk.api.receipt.domain.entity;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.entity.Orders;
import com.kiosk.api.payment.domain.entity.Payment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Receipt {
    private Orders orders;
    private List<OrderProduct> orderProducts;
    private Payment payment;
}
