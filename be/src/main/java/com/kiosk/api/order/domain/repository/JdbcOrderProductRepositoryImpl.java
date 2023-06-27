package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderProduct;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderProductRepositoryImpl implements OrderProductRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcOrderProductRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Long save(OrderProduct orderProduct) {
        String sql = "INSERT INTO order_product (order_product_name, order_product_cnt, order_product_size, order_product_temperature, order_id, product_id) "
                + "values (:name, :cnt, :size, :temperature, :orderId, :productId)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(orderProduct);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<OrderProduct> findAllBy(Long orderId) {
        String sql = "SELECT order_product_name, order_product_size, order_product_temperature, order_product_cnt "
                + "FROM order_product "
                + "WHERE order_id = :orderId";
        SqlParameterSource param = new MapSqlParameterSource("orderId", orderId);
        return template.query(sql, param, orderProductRowMapper());
    }

    private RowMapper<OrderProduct> orderProductRowMapper() {
        return (resultSet, rowNumber) -> OrderProduct.builder()
                    .name(resultSet.getString("order_product_name"))
                    .cnt(resultSet.getInt("order_product_cnt"))
                    .size(resultSet.getString("order_product_size"))
                    .temperature(resultSet.getString("order_product_temperature"))
                    .build();
    }

}
