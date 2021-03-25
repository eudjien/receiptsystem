package ru.clevertec.checksystem.webmvcjdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReceiptMapper implements RowMapper<Receipt> {
    @Override
    public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
        var receipt = new Receipt();
        receipt.setId(rs.getLong(Entities.Column.ID));
        receipt.setName(rs.getString(Entities.Column.NAME));
        receipt.setDescription(rs.getString(Entities.Column.DESCRIPTION));
        receipt.setDate(rs.getDate(Entities.Column.DATE));
        receipt.setCashier(rs.getString(Entities.Column.CASHIER));
        receipt.setAddress(rs.getString(Entities.Column.ADDRESS));
        receipt.setPhoneNumber(rs.getString(Entities.Column.PHONE_NUMBER));
        return receipt;
    }
}
