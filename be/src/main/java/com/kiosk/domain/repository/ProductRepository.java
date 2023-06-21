package com.kiosk.domain.repository;

import com.kiosk.domain.entity.Product;
import com.kiosk.web.controller.dto.MenuDto;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Long save(MenuDto menuDto);

    List<Product> findAll();

    Optional<Product> findBy(Long id);

    int deleteAll();
}
