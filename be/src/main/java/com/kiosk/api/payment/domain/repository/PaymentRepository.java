package com.kiosk.api.payment.domain.repository;

import com.kiosk.api.payment.domain.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    Long save(Payment payment);

    Optional<Payment> findByOrderId(Long orderId);

}
