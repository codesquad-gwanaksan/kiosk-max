package com.kiosk.api.product.web.controller;

import static com.kiosk.api.product.domain.entity.CategoryType.COFFEE;
import static java.nio.charset.StandardCharsets.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.kiosk.api.product.domain.entity.Category;
import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.web.controller.dto.ProductDto;
import com.kiosk.api.product.web.service.CategoryService;
import com.kiosk.api.product.web.service.ProductService;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;


@ActiveProfiles(profiles = {"test"})
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void beforeEach() {
        this.mockMvc = standaloneSetup(new ProductController(productService, categoryService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .defaultRequest(get("/products")
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
            .alwaysExpect(status().isOk())
            .alwaysDo(print())
            .build();

        Product product = Product.builder()
            .id(1L)
            .name("아메리카노")
            .price(4000L)
            .imageUrl("path")
            .isBest(false)
            .hasHot(true)
            .hasIce(true)
            .hasLarge(true)
            .hasSmall(true)
            .category(Category.builder().id(1L).categoryType(COFFEE).build())
            .build();
        List<ProductDto> productDtoList = new ArrayList<>(List.of(new ProductDto(product)));
        when(productService.findAll()).thenReturn(productDtoList);
        when(categoryService.findAll()).thenReturn(List.of(new Category(1L, COFFEE)));
    }

    @Test
    @DisplayName("'/products'를 요청하여 메뉴 정보들을 응답받는다")
    public void products() throws Exception {
        String expectByCategoryName = "$[%s].categoryName";
        String expectByCategoryId = "$[%s].categoryId";
        String expectByProducts = "$[%s].products[%s]";
        String expectByProductId = expectByProducts + ".productId";
        String expectByName = expectByProducts + ".name";
        String expectByPrice = expectByProducts + ".price";
        String expectByImgUrl = expectByProducts + ".imgUrl";
        String expectByHasHot = expectByProducts + ".hasHot";
        String expectByHasIce = expectByProducts + ".hasIce";
        String expectByHasLarge = expectByProducts + ".hasLarge";
        String expectByHasSmall = expectByProducts + ".hasSmall";
        String expectByProductCategoryId = expectByProducts + ".categoryId";
        String expectByIsBest = expectByProducts + ".isBest";

        mockMvc.perform(get("/products"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath(expectByCategoryName, 0).value(equalTo(COFFEE.name())))
            .andExpect(jsonPath(expectByCategoryId, 0).value(equalTo(1)))
            .andExpect(jsonPath(expectByProductId, 0, 0).value(equalTo(1)))
            .andExpect(jsonPath(expectByName, 0, 0).value("아메리카노"))
            .andExpect(jsonPath(expectByPrice, 0, 0).value(4000))
            .andExpect(jsonPath(expectByImgUrl, 0, 0).value("path"))
            .andExpect(jsonPath(expectByHasHot, 0, 0).value(true))
            .andExpect(jsonPath(expectByHasIce, 0, 0).value(true))
            .andExpect(jsonPath(expectByHasLarge, 0, 0).value(true))
            .andExpect(jsonPath(expectByHasSmall, 0, 0).value(true))
            .andExpect(jsonPath(expectByProductCategoryId, 0, 0).value(1))
            .andExpect(jsonPath(expectByIsBest, 0, 0).value(false));
    }
}
