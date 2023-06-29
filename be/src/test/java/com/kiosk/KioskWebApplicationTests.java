package com.kiosk;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.CartInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCardInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCashInDto;
import com.kiosk.api.payment.domain.dto.PaymentResultResponseDto;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.entity.PaymentMethod;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
class KioskWebApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    @DisplayName("/products 요청하여 메뉴들을 응답받습니다.")
    public void products() throws Exception {
        String expectByCategoryName = "$[%s].categoryName";
        String expectByCategoryId = "$[%s].categoryId";
        String expectByProducts = "$[%s].products";

        mockMvc.perform(get("/products"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath(expectByCategoryName, 0).exists())
            .andExpect(jsonPath(expectByCategoryId, 0).exists())
            .andExpect(jsonPath(expectByProducts, 0).isArray());
    }

    @Test
    @DisplayName("/api/payment/card 요청하여 카드 결제를 요청한다")
    @Transactional
    public void cardPayment() throws Exception {
        // given
        int totalPrice = 30400;
        List<CartInDto> orderProducts = new ArrayList<>();
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("large")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("small")
            .temperature("ice")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("small")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        PayByCardInDto payByCardInDto = PayByCardInDto.builder()
            .totalPrice(totalPrice)
            .orderProducts(orderProducts)
            .build();

        // when
        String json = this.mockMvc.perform(post("/api/payment/card")
                .content(new ObjectMapper().writeValueAsString(payByCardInDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(equalTo(true)))
            .andExpect(jsonPath("$.data.orderId").exists())
            .andExpect(jsonPath("$.errorCode.status").value(equalTo(200)))
            .andExpect(jsonPath("$.errorCode.code").value(equalTo("SUCCESS")))
            .andExpect(jsonPath("$.errorCode.message").value(equalTo("카드 결제 성공하였습니다.")))
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        PaymentResultResponseDto paymentResultResponseDto =
            new ObjectMapper().readValue(json, PaymentResultResponseDto.class);
        Long orderId = Long.parseLong(String.valueOf(paymentResultResponseDto.getData().get("orderId")));

        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow();
        List<OrderProduct> findOrderProducts = orderProductRepository.findAllBy(orderId);
        SoftAssertions.assertSoftly(softAssertions -> {
            // TODO: 주문검증

            // 결제검증
            softAssertions.assertThat(payment.getPaymentId()).isNotNull();
            softAssertions.assertThat(payment.getMethod()).isEqualTo(PaymentMethod.CARD);
            softAssertions.assertThat(payment.getRemainedPrice()).isEqualTo(0);
            softAssertions.assertThat(payment.getReceivedPrice()).isEqualTo(totalPrice);
            softAssertions.assertThat(payment.getTotalPrice()).isEqualTo(totalPrice);
            softAssertions.assertThat(payment.getOrderId()).isEqualTo(orderId);

            // 주문상품 검증
            softAssertions.assertThat(findOrderProducts.size()).isEqualTo(3);

            softAssertions.assertAll();
        });


    }


    @Test
    @DisplayName("/api/payment/card 요청하여 현금 결제를 요청한다")
    @Transactional
    public void cashPayment() throws Exception {
        // given
        int totalPrice = 30400;
        int receivedPrice = 31000;
        List<CartInDto> orderProducts = new ArrayList<>();
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("large")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("small")
            .temperature("ice")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("small")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        PayByCashInDto payByCashInDto = PayByCashInDto.builder()
            .totalPrice(totalPrice)
            .receivedPrice(receivedPrice)
            .orderProducts(orderProducts)
            .build();

        // when
        String json = this.mockMvc.perform(post("/api/payment/cash")
                .content(new ObjectMapper().writeValueAsString(payByCashInDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(equalTo(true)))
            .andExpect(jsonPath("$.data.orderId").exists())
            .andExpect(jsonPath("$.errorCode.status").value(equalTo(200)))
            .andExpect(jsonPath("$.errorCode.code").value(equalTo("SUCCESS")))
            .andExpect(jsonPath("$.errorCode.message").value(equalTo("현금 결제 성공하였습니다.")))
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // then
        PaymentResultResponseDto paymentResultResponseDto =
            new ObjectMapper().readValue(json, PaymentResultResponseDto.class);
        Long orderId = Long.parseLong(String.valueOf(paymentResultResponseDto.getData().get("orderId")));

        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow();
        List<OrderProduct> findOrderProducts = orderProductRepository.findAllBy(orderId);
        SoftAssertions.assertSoftly(softAssertions -> {
            // TODO: 주문검증

            // 결제검증
            softAssertions.assertThat(payment.getPaymentId()).isNotNull();
            softAssertions.assertThat(payment.getMethod()).isEqualTo(PaymentMethod.CASH);
            softAssertions.assertThat(payment.getRemainedPrice()).isEqualTo(600);
            softAssertions.assertThat(payment.getReceivedPrice()).isEqualTo(totalPrice);
            softAssertions.assertThat(payment.getTotalPrice()).isEqualTo(totalPrice);
            softAssertions.assertThat(payment.getOrderId()).isEqualTo(orderId);

            // 주문상품 검증
            softAssertions.assertThat(findOrderProducts.size()).isEqualTo(3);

            softAssertions.assertAll();
        });


    }

    @Test
    @DisplayName("/api/receipt 요청하여 주문 아이디에 따른 영수증 정보를 요청한다")
    @Transactional
    public void receipt() throws Exception {
        Long orderId = saveOrders();

        this.mockMvc.perform(get("/api/receipt")
                .param("orderId", String.valueOf(orderId)))
            .andDo(print())
            .andExpect(jsonPath("$.orderId").value(equalTo(orderId)))
            .andExpect(jsonPath("$.orderNumber").exists())
            .andExpect(jsonPath("$.orderProducts").isArray())
            .andExpect(jsonPath("$.payment.method").value(equalTo("cash")))
            .andExpect(jsonPath("$.payment.totalPrice").value(equalTo(30400)))
            .andExpect(jsonPath("$.payment.receivedPrice").value(equalTo(31000)))
            .andExpect(jsonPath("$.payment.remainedPrice").value(equalTo(600)))
            .andExpect(jsonPath("$.orderDatetime").exists());
    }

    private Long saveOrders() throws Exception {
        int totalPrice = 30400;
        int receivedPrice = 31000;
        List<CartInDto> orderProducts = new ArrayList<>();
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("large")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("small")
            .temperature("ice")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(CartInDto.builder()
            .productId(1L)
            .size("small")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        PayByCashInDto payByCashInDto = PayByCashInDto.builder()
            .totalPrice(totalPrice)
            .receivedPrice(receivedPrice)
            .orderProducts(orderProducts)
            .build();
        String json = this.mockMvc.perform(post("/api/payment/cash")
                .content(new ObjectMapper().writeValueAsString(payByCashInDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        PaymentResultResponseDto paymentResultResponseDto =
            new ObjectMapper().readValue(json, PaymentResultResponseDto.class);
        return Long.parseLong(String.valueOf(paymentResultResponseDto.getData().get("orderId")));
    }

}
