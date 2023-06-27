package com.kiosk.api.product.web.controller.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class ProductCategoryResponse {

    private String categoryName;
    private Long categoryId;
    private List<ProductDto> products;
}
