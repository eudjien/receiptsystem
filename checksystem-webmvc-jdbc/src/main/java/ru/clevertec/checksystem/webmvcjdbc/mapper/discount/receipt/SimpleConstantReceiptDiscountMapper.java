package ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receipt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SimpleConstantReceiptDiscountMapper implements RowMapper<SimpleConstantReceiptDiscount> {
    @Override
    public SimpleConstantReceiptDiscount mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new SimpleConstantReceiptDiscount();
        entity.setId(rs.getLong(Entities.Column.ID));
        entity.setConstant(rs.getBigDecimal(Entities.Column.CONSTANT));
        entity.setDescription(rs.getString(Entities.Column.DESCRIPTION));
        entity.setDependentDiscountId(rs.getLong(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID));
        return entity;
    }
}
