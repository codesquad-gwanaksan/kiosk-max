package com.kiosk.api.order.service;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.CartInDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public void saveOrderProductsWithOrderId(final Long orderId, final List<CartInDto> orderProducts) {
        for (PaymentRequestDto.CartInDto orderProduct : orderProducts) {
            OrderProduct completedOrderProduct = orderProduct.toEntity(orderId);
            orderProductRepository.save(completedOrderProduct);
        }
    }
}
