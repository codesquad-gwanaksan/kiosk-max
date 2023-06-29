package com.kiosk.api.order.domain.service;

import com.kiosk.api.order.domain.entity.OrderLog;
import com.kiosk.api.order.domain.repository.OrderLogRepository;
import com.kiosk.api.order.domain.repository.OrderProductRepository;
import com.kiosk.api.order.domain.repository.OrdersRepository;
import com.kiosk.api.product.domain.entity.Product;
import com.kiosk.api.product.domain.repository.CategoryRepository;
import com.kiosk.api.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderLogService {
    private final OrderLogRepository orderLogRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrdersRepository ordersRepository;

    // 매일 0시에 실행하는 메서드
    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void dailyActions() {
        // 주문 상품 테이블에서 상품별 판매량을 가져와서 일간 판매량 테이블에 저장
        LocalDate date = ZonedDateTime.now().minusDays(1).toLocalDate();
        List<OrderLog> orderLogList = orderProductRepository.findByDate(date);
        orderLogRepository.saveAll(orderLogList);

        // isBest 업데이트
        List<Product> bestProducts = findHighestSalesAmountByCategory(orderLogList);
        productRepository.updateBestProducts(bestProducts);

        // orderNumber 시퀀스 초기화
        ordersRepository.updateSequence(1L, "orderNumber");
    }


    public List<Product> findHighestSalesAmountByCategory(List<OrderLog> orderLogList) {
        Map<Long, Long> highestSalesAmountByCategory = new HashMap<>();
        Map<Long, List<Long>> highestProductIdByCategory = new HashMap<>();

        findSalesAmountByCategory(orderLogList, highestSalesAmountByCategory);

        // 모든 Product 레코드를 가져옴
        List<Product> products = productRepository.findAll();

        // 필요한 필드를 설정하여 결과 리스트에 추가
        List<Long> highestProductIds = findProductIdBySalesAmount(orderLogList, highestSalesAmountByCategory);

        // 가장 높은 salesAmount를 갖는 productId들을 모두 찾음
        return addResultListToSetField(products, highestProductIds);
    }

    private List<Product> addResultListToSetField(List<Product> products, List<Long> highestProductIds) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            // productId가 가장 높은 salesAmount를 갖는지 확인
            if (highestProductIds.contains(product.getId())) {
                // 필요한 필드를 설정
                //product.setBest(true);  // 예시로 isBest 필드를 true로 설정
                result.add(product);
            }
        }
        return result;
    }

    private List<Long> findProductIdBySalesAmount(List<OrderLog> orderLogList, Map<Long, Long> highestSalesAmountByCategory) {
        List<Long> highestProductIds = new ArrayList<>();
        for (OrderLog orderLog : orderLogList) {
            Long categoryId = orderLog.getCategoryId();
            Long productId = orderLog.getProductId();
            Long salesAmount = orderLog.getSalesAmount();

            if (salesAmount.equals(highestSalesAmountByCategory.get(categoryId))) {
                highestProductIds.add(productId);
            }
        }
        return highestProductIds;
    }

    private void findSalesAmountByCategory(List<OrderLog> orderLogList, Map<Long, Long> highestSalesAmountByCategory) {
        // 카테고리별로 가장 높은 salesAmount를 찾음
        for (OrderLog orderLog : orderLogList) {
            Long categoryId = orderLog.getCategoryId();
            Long salesAmount = orderLog.getSalesAmount();

            if (!highestSalesAmountByCategory.containsKey(categoryId) ||
                    salesAmount > highestSalesAmountByCategory.get(categoryId)) {
                highestSalesAmountByCategory.put(categoryId, salesAmount);
            }
        }
    }
}

//highestProductIdByCategory.computeIfAbsent(categoryId, key -> new ArrayList<>()).add(productId);
