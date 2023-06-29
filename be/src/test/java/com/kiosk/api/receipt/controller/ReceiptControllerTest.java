package com.kiosk.api.receipt.controller;

import static com.kiosk.api.payment.domain.entity.PaymentMethod.CARD;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.entity.Orders;
import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.receipt.domain.entity.Receipt;
import com.kiosk.api.receipt.service.ReceiptService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles(profiles = {"test"})
@WebMvcTest(ReceiptController.class)
class ReceiptControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ReceiptController receiptController;

    @MockBean
    private ReceiptService receiptService;

    @BeforeEach
    public void beforeEach() {
        Long orderId = 1L;
        this.mockMvc = MockMvcBuilders.standaloneSetup(receiptController)
            .defaultRequest(post("/api/receipt")
                .param("orderId", String.valueOf(orderId)))
            .alwaysExpect(status().isOk())
            .build();

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(OrderProduct.builder()
            .productId(1L)
            .size("large")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(OrderProduct.builder()
            .productId(1L)
            .size("small")
            .temperature("ice")
            .amount(2)
            .name("아메리카노")
            .build());
        orderProducts.add(OrderProduct.builder()
            .productId(1L)
            .size("small")
            .temperature("hot")
            .amount(2)
            .name("아메리카노")
            .build());
        Payment payment = Payment.builder()
            .paymentId(1L)
            .orderId(orderId)
            .totalPrice(10000)
            .receivedPrice(10000)
            .remainedPrice(0)
            .method(CARD)
            .build();

        Orders orders = Orders.builder()
            .orderId(orderId)
            .orderNumber(1L)
            .orderDateTime("2023-06-21 12:00:00")
            .build();

        Receipt receipt = new Receipt(orderId, orderProducts, payment, orders);
        Mockito.when(receiptService.getReceiptInformation(orderId)).thenReturn(receipt);
    }

    @Test
    @DisplayName("주문 아이디가 주어지고 영수증 정보를 요청할때 영수증 정보를 응답한다")
    public void receipt() throws Exception {
        // when, then
        this.mockMvc.perform(get("/api/receipt"))
            .andDo(print())
            .andExpect(jsonPath("$.orderId").value(equalTo(1)))
            .andExpect(jsonPath("$.orderNumber").value(equalTo(1)))
            .andExpect(jsonPath("$.orderProducts").exists())
            .andExpect(jsonPath("$.payment.method").value(equalTo("card")))
            .andExpect(jsonPath("$.payment.totalPrice").value(equalTo(10000)))
            .andExpect(jsonPath("$.payment.receivedPrice").value(equalTo(10000)))
            .andExpect(jsonPath("$.payment.remainedPrice").value(equalTo(0)))
            .andExpect(jsonPath("$.orderDatetime").value(equalTo("2023-06-21 12:00:00")));
    }
}
