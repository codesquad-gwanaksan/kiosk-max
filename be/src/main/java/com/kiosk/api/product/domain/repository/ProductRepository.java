package com.kiosk.api.product.domain.repository;

import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.web.controller.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Long save(ProductDto productDto);

    List<Product> findAll();

    Optional<Product> findBy(Long id);

    int deleteAll();
}
