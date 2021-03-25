package ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receiptItem;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ThresholdPercentageReceiptItemDiscountMapper implements RowMapper<ThresholdPercentageReceiptItemDiscount> {
    @Override
    public ThresholdPercentageReceiptItemDiscount mapRow(ResultSet rs, int rowNum) throws SQLException {
        var entity = new ThresholdPercentageReceiptItemDiscount();
        entity.setId(rs.getLong(Entities.Column.ID));
        entity.setPercent(rs.getDouble(Entities.Column.PERCENT));
        entity.setThreshold(rs.getLong(Entities.Column.THRESHOLD));
        entity.setDescription(rs.getString(Entities.Column.DESCRIPTION));
        entity.setDependentDiscountId(rs.getLong(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID));
        return entity;
    }
}
