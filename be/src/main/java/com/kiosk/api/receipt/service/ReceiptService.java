package com.kiosk.api.receipt.service;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.entity.Orders;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.order.domain.repository.OrdersRepository;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import com.kiosk.api.receipt.domain.entity.Receipt;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private final OrderProductRepository orderProductRepository;
    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    public ReceiptService(OrderProductRepository orderProductRepository, PaymentRepository paymentRepository,
                          OrdersRepository ordersRepository) {
        this.orderProductRepository = orderProductRepository;
        this.paymentRepository = paymentRepository;
        this.ordersRepository = ordersRepository;
    }

    public Receipt getReceiptInformation(Long orderId) {
        List<OrderProduct> products = orderProductRepository.findAllBy(orderId);
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(); // TODO: 예외처리
        Orders orders = ordersRepository.findBy(orderId).orElseThrow();
        return new Receipt(orderId, products, payment, orders);
    }

}
