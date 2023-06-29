package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderLog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcOrderLogRepositoryImpl implements OrderLogRepository {
    private final NamedParameterJdbcTemplate template;

    @Override
    public Map<Long, Long> saveAll(List<OrderLog> orderLogs) {
        Map<Long, Long> sales = new HashMap<>();

        for (OrderLog orderLog : orderLogs) {
            sales.put(save(orderLog)[0], save(orderLog)[1]);
        }

        return sales;
    }

    private Long[] save(OrderLog orderLog) {
        String sql = "INSERT INTO order_log (sales_date, category_id, product_id, sales_amount) " // amount로 db 수정
                + "values (:salesDate, :categoryId, :productId, :salesAmount)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(orderLog);

        template.update(sql, param);

        return new Long[]{orderLog.getProductId(), orderLog.getSalesAmount()};
    }
}

