package com.kiosk.domain.repository;

import static com.kiosk.domain.entity.CategoryType.*;

import com.kiosk.domain.entity.Category;
import com.kiosk.domain.entity.Product;
import com.kiosk.web.controller.dto.ProductDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository = new MemoryProductRepository();
    }

    @Test
    @DisplayName("상품에 대한 정보가 주어지고 저장을 요청할때 상품이 저장된다")
    public void save() {
        // given
        Category category = Category.builder()
            .categoryType(COFFEE)
            .build();
        Product product = Product.builder()
            .name("아메리카노")
            .price(4000L)
            .isBest(false)
            .hasHot(true)
            .hasIce(true)
            .hasLarge(true)
            .hasSmall(true)
            .category(category)
            .build();
        ProductDto productDto = new ProductDto(product);
        // when
        Long id = productRepository.save(productDto);
        // then
        Product findProduct = productRepository.findBy(id).orElseThrow();
        SoftAssertions.assertSoftly(softAssertions ->
            softAssertions.assertThat(findProduct.getId()).isEqualTo(id));
    }
}
