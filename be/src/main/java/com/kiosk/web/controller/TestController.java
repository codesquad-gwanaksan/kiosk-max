package com.kiosk.web.controller;

import com.kiosk.web.controller.dto.MenuDto;
import com.kiosk.web.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final ProductService productService;

    @PostMapping("/addMenu")
    public String add(@RequestBody MenuDto menuDto) {
        logger.debug("menuDto : {}", menuDto);
        Long id = productService.save(menuDto);
        return "success : " + id;
    }
}
