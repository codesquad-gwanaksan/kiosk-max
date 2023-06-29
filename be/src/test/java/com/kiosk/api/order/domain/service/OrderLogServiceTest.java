package com.kiosk.api.order.domain.service;

import com.kiosk.api.order.domain.entity.OrderLog;
import com.kiosk.api.order.domain.repository.OrderLogRepository;
import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.domain.repository.ProductRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class OrderLogServiceTest {

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private OrderLogRepository orderLogRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("자정이 될때 order_log 테이블에 판매량을 기록하고 isBest를 갱신하고 주문번호를 초기화한다")
    @Transactional
    public void dailyActions() {
        // given

        // when
        orderLogService.dailyActions();
        // then
        List<OrderLog> orderLogs = orderLogRepository.findAllByDate(LocalDate.of(2023, 6, 28));
        List<Product> bestProducts = productRepository.findAll().stream()
            .filter(Product::isBest)
            .collect(Collectors.toUnmodifiableList());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(orderLogs.size()).isEqualTo(9);
            softAssertions.assertThat(bestProducts.size()).isEqualTo(5);
            softAssertions.assertAll();
        });
    }
}
