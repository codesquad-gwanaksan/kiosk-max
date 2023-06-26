package com.kiosk.api.product.web.service;

import com.kiosk.api.product.domain.entity.Category;
import com.kiosk.api.product.domain.repository.CategoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
