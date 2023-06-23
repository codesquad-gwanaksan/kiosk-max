package com.kiosk.web.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ProductCategoryResponse {

    private String categoryName;
    private Long categoryId;
    private List<ProductDto> products;
}
