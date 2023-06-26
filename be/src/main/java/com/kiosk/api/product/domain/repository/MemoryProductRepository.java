package com.kiosk.api.product.domain.repository;

import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.web.controller.dto.ProductDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryProductRepository implements ProductRepository {

    private static final Map<Long, Product> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    public Long save(final ProductDto productDto) {
        Long id = nextId();
        productDto.setProductId(id);
        store.put(id, productDto.toEntity());
        return id;
    }

    public List<Product> findAll() {
        return store.values().stream()
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Product> findBy(final Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public int deleteAll() {
        int delSize = store.size();
        store.clear();
        return delSize;
    }

    public synchronized static Long nextId() {
        return ++sequence;
    }
}
