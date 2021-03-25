package ru.clevertec.checksystem.webmvcjdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReceiptItemMapper implements RowMapper<ReceiptItem> {
    @Override
    public ReceiptItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        var receiptItem = new ReceiptItem();
        receiptItem.setId(rs.getLong(Entities.Column.ID));
        receiptItem.setQuantity(rs.getLong(Entities.Column.QUANTITY));
        receiptItem.setReceiptId(rs.getLong(Entities.JoinColumn.RECEIPT_ID));
        receiptItem.setProductId(rs.getLong(Entities.JoinColumn.PRODUCT_ID));
        return receiptItem;
    }
}
