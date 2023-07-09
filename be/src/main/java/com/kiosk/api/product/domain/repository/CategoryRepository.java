package com.kiosk.api.product.domain.repository;

import com.kiosk.api.product.domain.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Long save(Category category);

    List<Category> findAll();

    Optional<Category> findBy(Long id);

    Optional<Category> findBy(String name);

    int deleteAll();
}
