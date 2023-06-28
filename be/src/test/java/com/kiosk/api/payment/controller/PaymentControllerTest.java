package com.kiosk.api.payment.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.CartInDto;
import com.kiosk.api.payment.domain.dto.PaymentRequestDto.PayByCardInDto;
import com.kiosk.api.payment.domain.repository.PaymentRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles(profiles = {"test"})
class PaymentControllerTest {

    @Nested
    @DisplayName("카드 결제 테스트")
    @WebMvcTest(PaymentController.class)
    public class CardPaymentTest {

        private MockMvc mockMvc;

        @Autowired
        private PaymentController paymentController;

        @MockBean
        private PaymentRepository paymentRepository;

        @MockBean
        private OrderProductRepository orderProductRepository;

        @BeforeEach
        public void beforeEach() throws JsonProcessingException {
            int totalPrice = 10000;
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

            this.mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .defaultRequest(post("/api/payment/card")
                    .content(new ObjectMapper().writeValueAsString(payByCardInDto))
                    .contentType(APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .build();
        }

        @Test
        @DisplayName("카드 결제 정보가 주어지고 결제를 요청할때 결제 정보가 저장된다.")
        public void pay() throws Exception {
            // given

            // when, then
            this.mockMvc.perform(post("/api/payment/card"))
                .andExpect(jsonPath("$.success").value(equalTo(true)))
                .andExpect(jsonPath("$.data.orderId").value(equalTo(1)))
                .andExpect(jsonPath("$.errorCode.status").value(equalTo(200)))
                .andExpect(jsonPath("$.errorCode.code").value(equalTo("SUCCESS")))
                .andExpect(jsonPath("$.errorCode.message").value(equalTo("카드 결제 성공하였습니다.")));
        }

        @Test
        @DisplayName("카드 결제 정보가 주어지고 쿼리스트링으로 fail=400 전달하며 결제 요청시 결제가 실패된다")
        public void pay_fail_400() throws Exception {
            // given

            // when, then
            this.mockMvc.perform(post("/api/payment/card")
                    .param("fail", "400"))
                .andExpect(jsonPath("$.success").value(equalTo(false)))
                .andExpect(jsonPath("$.data.orderId").doesNotExist())
                .andExpect(jsonPath("$.errorCode.status").value(equalTo(400)))
                .andExpect(jsonPath("$.errorCode.code").value(equalTo("PaymentError")))
                .andExpect(jsonPath("$.errorCode.message").value(equalTo("결제가 실패했습니다. 잠시후에 시도해주세요.")));
        }

        @Test
        @DisplayName("카드 결제 정보가 주어지고 쿼리스트링으로 fail=500 전달하며 결제 요청시 서버 에러가 발생한다")
        public void pay_fail_500() throws Exception {
            // given

            // when, then
            this.mockMvc.perform(post("/api/payment/card")
                    .param("fail", "500"))
                .andExpect(jsonPath("$.success").value(equalTo(false)))
                .andExpect(jsonPath("$.data.orderId").doesNotExist())
                .andExpect(jsonPath("$.errorCode.status").value(equalTo(500)))
                .andExpect(jsonPath("$.errorCode.code").value(equalTo("ServerError")))
                .andExpect(jsonPath("$.errorCode.message").value(equalTo("서버 에러입니다. 잠시 후에 이용해주세요.")));
        }
    }
    
}
