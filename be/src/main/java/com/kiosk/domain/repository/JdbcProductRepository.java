package com.kiosk.domain.repository;

import com.kiosk.domain.entity.Category;
import com.kiosk.domain.entity.Product;
import com.kiosk.web.controller.dto.ProductDto;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate template;
    private final CategoryRepository categoryRepository;

    public JdbcProductRepository(final JdbcTemplate template, final CategoryRepository categoryRepository) {
        this.template = template;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long save(final ProductDto dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            Category category = categoryRepository.findBy(dto.getCategoryId()).orElseThrow();
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO product(product_name, product_price, product_image, product_is_best, product_has_hot, "
                    + "product_has_ice, product_has_large, product_has_small, category_id) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", new String[]{
                    "product_id"}); // KeyHolder에 자동생성된 값을 넣기 위해서 두번째 매개변수에 배열을 넣어야 합니다. 배열의 요소에는 pk 컬럼명을 넣어야 합니다.
            ps.setString(1, dto.getName());
            ps.setLong(2, dto.getPrice());
            ps.setString(3, dto.getImgUrl());
            ps.setBoolean(4, dto.isBest());
            ps.setBoolean(5, dto.isHasHot());
            ps.setBoolean(6, dto.isHasIce());
            ps.setBoolean(7, dto.isHasLarge());
            ps.setBoolean(8, dto.isHasSmall());
            ps.setLong(9, category.getId());
            return ps;
        }, keyHolder);
        // BigInteger to Long으로 캐스팅하기 위해서 longValue를 호출합니다.
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Product> findAll() {
        return template.query(
            "SELECT product_id, product_name, product_price, product_image, product_is_best, product_has_hot, product_has_ice, product_has_large, product_has_small, category_id "
                + "FROM product", productRowMapper());
    }

    @Override
    public Optional<Product> findBy(final Long id) {
        List<Product> products = template.query(
            "SELECT product_id, product_name, product_price, product_image, product_is_best, product_has_hot, product_has_ice, product_has_large, product_has_small, category_id "
                + "FROM product WHERE product_id = ?", productRowMapper(), id);
        return products.stream().findAny();
    }

    @Override
    public int deleteAll() {
        return template.update("DELETE FROM product");
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Category category = categoryRepository.findBy(rs.getLong("category_id")).orElseThrow();
            return Product.builder()
                .id(rs.getLong("product_id"))
                .name(rs.getString("product_name"))
                .price(rs.getLong("product_price"))
                .imageUrl(rs.getString("product_image"))
                .isBest(rs.getBoolean("product_is_best"))
                .hasHot(rs.getBoolean("product_has_hot"))
                .hasIce(rs.getBoolean("product_has_ice"))
                .hasLarge(rs.getBoolean("product_has_large"))
                .hasSmall(rs.getBoolean("product_has_small"))
                .category(category)
                .build();
        };
    }
}
