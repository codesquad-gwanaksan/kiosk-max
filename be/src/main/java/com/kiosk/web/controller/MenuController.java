package com.kiosk.web.controller;

import com.kiosk.domain.entity.Category;
import com.kiosk.domain.repository.CategoryRepository;
import com.kiosk.web.controller.dto.MenuCategoryResponse;
import com.kiosk.web.controller.dto.MenuDto;
import com.kiosk.web.service.CategoryService;
import com.kiosk.web.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MenuController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/menus")
    public List<MenuCategoryResponse> list() {
        List<MenuCategoryResponse> responses = new ArrayList<>();
        List<MenuDto> menus = productService.findAll(); // 전체 메뉴
        List<Category> categories = categoryService.findAll(); // 카테고리 정보들
        for (Category category : categories) {
            List<MenuDto> menuByCategory = menus.stream()
                .filter(menu -> menu.matchCategoryId(category))
                .collect(Collectors.toUnmodifiableList());
            responses.add(new MenuCategoryResponse(category.getCategoryType().name(),
                category.getId(),
                menuByCategory));
        }
        return responses;
    }
}
