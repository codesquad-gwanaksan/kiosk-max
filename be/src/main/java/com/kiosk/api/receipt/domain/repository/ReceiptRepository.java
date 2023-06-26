package com.kiosk.api.receipt.domain.repository;

import com.kiosk.api.receipt.domain.entity.Receipt;
import java.util.List;
import java.util.Optional;

public interface ReceiptRepository {

    Long save();

    List<Receipt> findAll();

    Optional<Receipt> findBy(Long id);

    Optional<Receipt> findBy(String name);

    int deleteAll();

}
