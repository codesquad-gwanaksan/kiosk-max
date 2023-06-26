package com.kiosk.api.payment.domain.repository;

import com.kiosk.api.payment.domain.entity.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentCardRepository {

    Long save();

    List<Payment> findAll();

    Optional<Payment> findBy(Long id);

    Optional<Payment> findBy(String name);

    int deleteAll();

}
