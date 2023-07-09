package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderLog;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
            Long[] savedOrderLog = save(orderLog);
            sales.put(savedOrderLog[0], savedOrderLog[1]);
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

    @Override
    public List<OrderLog> findAllByDate(final LocalDate date) {
        String sql = "SELECT product_id, category_id, sales_amount, sales_date " +
            "FROM order_log " +
            "WHERE DATE(sales_date) = STR_TO_DATE(:date, '%Y-%m-%d')";

        SqlParameterSource param = new MapSqlParameterSource("date", date.toString());

        return template.query(sql, param, rowMapper());
    }

    private RowMapper<OrderLog> rowMapper() {
        return (rs, rowNum) -> OrderLog.builder()
            .productId(rs.getLong("product_id"))
            .categoryId(rs.getLong("category_id"))
            .salesAmount(rs.getLong("sales_amount"))
            .salesDate(rs.getDate("sales_date").toString())
            .build();
    }

}

