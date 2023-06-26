package com.kiosk.api.product.web.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiosk.api.product.web.controller.dto.ProductCategoryResponse;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("'/products'를 요청하여 메뉴 정보들을 응답받는다")
    public void products() throws Exception {
        // given
        String url = "/products";
        // when
        String json = mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(UTF_8);
        // then
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductCategoryResponse> products = objectMapper.readValue(json, new TypeReference<>() {
        });
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(products.size()).isEqualTo(5);
        });
    }
}