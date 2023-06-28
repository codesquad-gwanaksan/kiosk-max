package com.kiosk.api.payment.domain.repository;

import com.kiosk.api.payment.domain.entity.Payment;
import com.kiosk.api.payment.domain.entity.PaymentMethod;
import java.sql.Types;
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
        String sql =
            "INSERT INTO payment (order_id, payment_total_price, payment_received_price, payment_remained_price, payment_method) "
                + "values (:orderId, :totalPrice, :receivedPrice, :remainedPrice, :method)";

        BeanPropertySqlParameterSource param = getBeanPropertySqlParameterSourcePayment(payment);
        param.registerSqlType("payment_method", Types.VARCHAR);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private BeanPropertySqlParameterSource getBeanPropertySqlParameterSourcePayment(Payment payment) {
        return new BeanPropertySqlParameterSource(payment) {
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object value = super.getValue(paramName);
                if (value instanceof Enum) {
                    return value.toString();
                }
                return value;
            }
        };
    }

    @Override
    public Optional<Payment> findBy(final Long paymentId) {
        String sql =
            "SELECT payment_id, order_id, payment_total_price, payment_received_price, payment_remained_price, payment_method "
                + "FROM payment "
                + "WHERE payment_id = :paymentId";

        SqlParameterSource param = new MapSqlParameterSource("paymentId", paymentId);
        return template.query(sql, param, rowMapper()).stream().findAny();
    }

    // 1:1 이니까 1개만 가져오고 싶은데
    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        String sql =
            "SELECT payment_id, order_id, payment_total_price, payment_received_price, payment_remained_price, payment_method "
                + "FROM payment "
                + "WHERE order_id = :orderId";

        SqlParameterSource param = new MapSqlParameterSource("orderId", orderId);
        return template.query(sql, param, rowMapper()).stream().findAny();
    }

    private RowMapper<Payment> rowMapper() {
        return (rs, rn) -> Payment.builder()
            .paymentId(rs.getLong("payment_id"))
            .orderId(rs.getLong("order_id"))
            .totalPrice(rs.getInt("payment_total_price"))
            .receivedPrice(rs.getInt("payment_received_price"))
            .remainedPrice(rs.getInt("payment_remained_price"))
            .method(PaymentMethod.from(rs.getString("payment_method")))
            .build();
    }
}
