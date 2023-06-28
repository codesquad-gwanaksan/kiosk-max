package com.kiosk.api.order.domain.repository;

import com.kiosk.api.order.domain.entity.OrderProduct;
import com.kiosk.api.order.domain.entity.Orders;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcOrdersRepositoryImpl implements OrdersRepository {

    private static int today;
    private static Long orderNumber = 0L;

    private final NamedParameterJdbcTemplate template;

    public JdbcOrdersRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    static {
        dailyReset();
        setToday();
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public static void setToday() {
        today = ZonedDateTime.now().toLocalDate().getDayOfMonth();
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public static void dailyReset() {
        LocalDate current = ZonedDateTime.now().toLocalDate();
        if (current.getDayOfMonth() - today >= 1) {
            orderNumber = 0L;
        }
    }

    @Override
    public Integer save(Orders orders) {
        orders.setOrderNumber(orderNumber++);
        String sql = "INSERT INTO orders (order_id, order_number, order_datetime) "
            + "values (:orderId, :orderNumber, :orderDatetime)";

        SqlParameterSource param =
            new MapSqlParameterSource("orders", orders)
                .addValue("orderId", orders.getOrderId())
                .addValue("orderNumber", orders.getOrderNumber())
                .addValue("orderDatetime", orders.getOrderDateTime());
        return template.update(sql, param);
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
        return (rs, rowNum) -> new Orders(
            rs.getLong("order_id"),
            rs.getLong("order_number"),
            rs.getString("order_datetime")
        );
    }

    @Override
    public Long findSequence(String name) {
        String query = "SELECT value FROM sequence WHERE name = :name";
        SqlParameterSource param = new MapSqlParameterSource("name", name);
        Long sequenceValue = template.queryForObject(query, param, Long.class);
        return sequenceValue != null ? sequenceValue : null;
    }

    @Override
    public void updateSequence(Long value, String name) {
        String query = "UPDATE sequence SET value = :value WHERE name = :name";
        SqlParameterSource param = new MapSqlParameterSource("sequence", value);
        template.update(query, param);
    }
}
