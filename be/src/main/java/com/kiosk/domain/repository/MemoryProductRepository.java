package com.kiosk.domain.repository;

import com.kiosk.domain.entity.Product;
import com.kiosk.web.controller.dto.MenuDto;
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

    public Long save(final MenuDto menuDto) {
        Long id = nextId();
        menuDto.setMenuId(id);
        store.put(id, menuDto.toEntity());
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
