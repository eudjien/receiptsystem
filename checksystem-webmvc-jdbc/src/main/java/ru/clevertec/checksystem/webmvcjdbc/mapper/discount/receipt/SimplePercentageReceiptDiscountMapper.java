package ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receipt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SimplePercentageReceiptDiscountMapper implements RowMapper<SimplePercentageReceiptDiscount> {
    @Override
    public SimplePercentageReceiptDiscount mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new SimplePercentageReceiptDiscount();
        entity.setId(rs.getLong(Entities.Column.ID));
        entity.setPercent(rs.getDouble(Entities.Column.PERCENT));
        entity.setDescription(rs.getString(Entities.Column.DESCRIPTION));
        entity.setDependentDiscountId(rs.getLong(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID));
        return entity;
    }
}
