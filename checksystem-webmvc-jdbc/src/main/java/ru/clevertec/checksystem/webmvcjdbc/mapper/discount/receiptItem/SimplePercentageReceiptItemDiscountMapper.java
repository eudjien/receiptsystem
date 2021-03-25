package ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receiptItem;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SimplePercentageReceiptItemDiscountMapper implements RowMapper<SimplePercentageReceiptItemDiscount> {
    @Override
    public SimplePercentageReceiptItemDiscount mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new SimplePercentageReceiptItemDiscount();
        entity.setId(rs.getLong(Entities.Column.ID));
        entity.setPercent(rs.getDouble(Entities.Column.PERCENT));
        entity.setDescription(rs.getString(Entities.Column.DESCRIPTION));
        entity.setDependentDiscountId(rs.getLong(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID));
        return entity;
    }
}
