package com.kiosk.api.product.web.controller;

import com.kiosk.api.product.web.controller.dto.ProductDto;
import com.kiosk.api.product.web.service.ProductService;
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

    @PostMapping("/addProduct")
    public String add(@RequestBody ProductDto productDto) {
        logger.debug("productDto : {}", productDto);
        Long id = productService.save(productDto);
        return "success : " + id;
    }
}
