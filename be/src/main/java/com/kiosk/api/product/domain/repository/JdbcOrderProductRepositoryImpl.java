package com.kiosk.api.product.domain.repository;

import com.kiosk.api.product.web.controller.dto.OrderProductResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcOrderProductRepositoryImpl implements OrderProductRepository {
}
