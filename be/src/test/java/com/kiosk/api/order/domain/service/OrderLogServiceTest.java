package com.kiosk.api.order.domain.service;

import static com.kiosk.api.payment.domain.entity.PaymentMethod.CARD;

import com.kiosk.api.order.domain.entity.OrderLog;
import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.entity.Orders;
import com.kiosk.api.order.domain.repository.OrderLogRepository;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.order.domain.repository.OrdersRepository;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.domain.repository.ProductRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("자정이 될때 order_log 테이블에 판매량을 기록하고 isBest를 갱신하고 주문번호를 초기화한다")
    @Transactional
    public void dailyActions() {
        // given
        // 주문 저장
        ordersRepository.save(Orders.builder().orderId(1L).orderNumber(1L).orderDateTime("2023-06-28 08:00:00").build());
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(1L).name("아메리카노").amount(1).size("small").temperature("ice")
                .build());
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(2L).name("카라멜 마키아또").amount(2).size("small").temperature("hot")
                .build());
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(3L).name("롱블랙").amount(3).size("small").temperature("ice")
                .build());
        orderProducts.add(OrderProduct.builder().orderId(1L).productId(4L).name("프렌치 아메리카노").amount(4).size("small")
            .temperature("ice").build());
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(6L).name("카페 라떼").amount(1).size("small").temperature("ice")
                .build());
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(7L).name("초콜릿 라떼").amount(1).size("large").temperature("ice")
                .build());
        orderProducts.add(OrderProduct.builder().orderId(1L).productId(11L).name("민트 블렌드 티").amount(1).size("large")
            .temperature("hot").build());
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(12L).name("블루밍 프루트 유스베리 티").amount(2).size("small")
                .temperature("hot").build());
        orderProducts.add(
            OrderProduct.builder().orderId(1L).productId(16L).name("딸기 주스").amount(1).size("small").temperature("ice")
                .build());
        // 주문상품 저장
        orderProducts.forEach(orderProduct -> orderProductRepository.save(orderProduct));

        // 결제 저장
        paymentRepository.save(
            Payment.builder().paymentId(1L).orderId(1L).method(CARD).totalPrice(10000).receivedPrice(10000)
                .remainedPrice(0).build());

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
