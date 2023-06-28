package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.Orders;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcOrdersRepositoryImpl implements OrdersRepository {
    private final NamedParameterJdbcTemplate template;

    public JdbcOrdersRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Orders> findBy(Long orderId) {
        String sql = "SELECT order_id, order_number, order_datetime "
                + "FROM orders "
                + "WHERE order_id = :orderId";

        SqlParameterSource param = new MapSqlParameterSource("orderId", orderId);

        return template.query(sql, param, rowMapper()).stream().findAny();
    }

    private RowMapper<Orders> rowMapper() {
        return (rs, rowNum) -> Orders.builder()
                .orderNumber(rs.getLong("order_number"))
                .orderDateTime(rs.getString("order_datetime"))
                .build();
    }
}
