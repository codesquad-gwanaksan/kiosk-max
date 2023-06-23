package com.kiosk.domain.repository;

import com.kiosk.domain.entity.Product;
import com.kiosk.web.controller.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Long save(ProductDto productDto);

    List<Product> findAll();

    Optional<Product> findBy(Long id);

    int deleteAll();
}
