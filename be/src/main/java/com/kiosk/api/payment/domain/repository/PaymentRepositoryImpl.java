package com.kiosk.api.payment.domain.repository;

import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.entity.PaymentMethod;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    // 결제 -> order id, total amount, received amount, change, method
    private final NamedParameterJdbcTemplate template;

    public PaymentRepositoryImpl(final NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Long save(Payment payment) {
        String sql = "INSERT INTO payment (order_id, payment_total_price, payment_received_price, payment_remained_price, payment_method) "
                + "values (:orderId, :totalPrice, :receivedPrice, :remainedPrice, :method)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(payment);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    // 1:1 이니까 1개만 가져오고 싶은데
    @Override
    public Optional<Payment> findBy(Long orderId) {
        String sql = "SELECT order_id, payment_total_price, payment_received_price, payment_remained_price, payment_method "
                + "FROM payment "
                + "WHERE order_id = :orderId";

        SqlParameterSource param = new MapSqlParameterSource("orderId", orderId);

        // ResultSetExtractor 은 무엇?
        Payment payment = template.queryForObject(sql, param, rowMapper());

        return Optional.ofNullable(payment);
    }

    private RowMapper<Payment> rowMapper() {
        return (rs, rn) -> Payment.builder()
                .orderId(rs.getLong("order_id"))
                .totalPrice(rs.getInt("payment_total_price"))
                .receivedPrice(rs.getInt("payment_received_price"))
                .remainedPrice(rs.getInt("payment_remained_price"))
                .method(PaymentMethod.from(rs.getString("payment_method")))
                .build();
    }
}
