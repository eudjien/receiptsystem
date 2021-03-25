package ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receiptItem;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SimpleConstantReceiptItemDiscountMapper implements RowMapper<SimpleConstantReceiptItemDiscount> {
    @Override
    public SimpleConstantReceiptItemDiscount mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new SimpleConstantReceiptItemDiscount();
        entity.setId(rs.getLong(Entities.Column.ID));
        entity.setConstant(rs.getBigDecimal(Entities.Column.CONSTANT));
        entity.setDescription(rs.getString(Entities.Column.DESCRIPTION));
        entity.setDependentDiscountId(rs.getLong(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID));
        return entity;
    }
}
