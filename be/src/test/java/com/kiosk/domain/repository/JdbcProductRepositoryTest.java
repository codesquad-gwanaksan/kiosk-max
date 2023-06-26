package com.kiosk.domain.repository;

import static com.kiosk.api.product.domain.entity.CategoryType.COFFEE;
import static com.kiosk.api.product.domain.entity.CategoryType.JUICE;
import static com.kiosk.api.product.domain.entity.CategoryType.LATTE;
import static com.kiosk.api.product.domain.entity.CategoryType.SPARKLING;
import static com.kiosk.api.product.domain.entity.CategoryType.TEA;

import com.kiosk.api.product.domain.entity.Category;
import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.domain.repository.CategoryRepository;
import com.kiosk.api.product.domain.repository.ProductRepository;
import com.kiosk.api.product.web.controller.dto.ProductDto;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JdbcProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        categoryRepository.save(Category.builder().categoryType(COFFEE).build());
        categoryRepository.save(Category.builder().categoryType(JUICE).build());
        categoryRepository.save(Category.builder().categoryType(TEA).build());
        categoryRepository.save(Category.builder().categoryType(LATTE).build());
        categoryRepository.save(Category.builder().categoryType(SPARKLING).build());
    }

    @Transactional
    @Test
    @DisplayName("상품 정보가 주어지고 저장 요청을 했을때 저장소에 저장된다")
    public void save() {
        // given
        Long category_id = categoryRepository.findBy(COFFEE.name()).orElseThrow().getId();
        Category category = Category.builder().id(category_id).categoryType(COFFEE).build();
        Product product = Product.builder()
            .name("아메리카노")
            .price(4000L)
            .imageUrl("path")
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
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(findProduct.getId()).isEqualTo(id);
        });
    }

    @Test
    @DisplayName("상품 정보들을 요청하고 성공적으로 응답한다")
    @Transactional
    public void findAll() {
        // given
        Long category_id = categoryRepository.findBy(COFFEE.name()).orElseThrow().getId();
        Category category = Category.builder().id(category_id).categoryType(COFFEE).build();
        Product product = Product.builder()
            .name("아메리카노")
            .price(4000L)
            .imageUrl("path")
            .isBest(false)
            .hasHot(true)
            .hasIce(true)
            .hasLarge(true)
            .hasSmall(true)
            .category(category)
            .build();
        ProductDto productDto = new ProductDto(product);
        productRepository.save(productDto);
        // when
        List<Product> products = productRepository.findAll();
        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(products.size()).isEqualTo(1);
            softAssertions.assertThat(products.get(0).getName()).isEqualTo("아메리카노");
        });
    }
}
