package ru.clevertec.checksystem.webmvcjdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        var product = new Product();
        product.setId(rs.getLong(Entities.Column.ID));
        product.setName(rs.getString(Entities.Column.NAME));
        product.setPrice(rs.getBigDecimal(Entities.Column.PRICE));
        return product;
    }
}
