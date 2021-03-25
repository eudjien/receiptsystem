package ru.clevertec.checksystem.webmvcjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.webmvcjdbc.mapper.ReceiptMapper;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReceiptDao extends Dao<Receipt, Long> {

    private final static String SQL_FIND_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPTS + " WHERE id = :id";
    private final static String SQL_FIND_ALL = "SELECT * FROM " + Entities.Table.RECEIPTS;
    private final static String SQL_FIND_ALL_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPTS + " WHERE id IN (:ids)";

    private final static String SQL_DELETE_ALL = "DELETE FROM " + Entities.Table.RECEIPTS;
    private final static String SQL_DELETE_BY_ID = "DELETE FROM " + Entities.Table.RECEIPTS + " WHERE id = :id";
    private final static String SQL_DELETE_ALL_BY_ID = "DELETE FROM " + Entities.Table.RECEIPTS + " WHERE id IN(:ids)";

    private final static String SQL_UPDATE_BY_ID = "" +
            "UPDATE " + Entities.Table.RECEIPTS + " " +
            "SET name = :name, address = :address, cashier = :cashier, date = :date, description = :description, phone_number = :phone_number " +
            "WHERE id = :id";

    private final static String SQL_INSERT = "" +
            "INSERT INTO " + Entities.Table.RECEIPTS + "(id, name, description, address, cashier, date, phone_number) " +
            "VALUES(:id, :name, :description, :address, :cashier, :date, :phone_number)";

    private final ReceiptMapper receiptMapper;

    @Autowired
    public ReceiptDao(DataSource dataSource, ReceiptMapper receiptMapper) {
        super(new NamedParameterJdbcTemplate(dataSource));
        this.receiptMapper = receiptMapper;
    }

    @Override
    public List<Receipt> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL, receiptMapper);
    }

    @Override
    public List<Receipt> findAllById(Collection<Long> ids) {
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().query(SQL_FIND_ALL_BY_ID, parameters, receiptMapper);
    }

    @Override
    public Receipt findById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, parameters, receiptMapper);
    }

    @Override
    public int deleteById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public int delete(Receipt entity) {
        var parameters = new MapSqlParameterSource().addValue("id", entity.getId());
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public <S extends Receipt> int deleteAll(Collection<S> entities) {
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().update(SQL_DELETE_ALL_BY_ID, parameters);
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(SQL_DELETE_ALL, new MapSqlParameterSource());
    }

    @Override
    public Receipt update(Receipt entity) {
        getJdbcTemplate().update(SQL_UPDATE_BY_ID, createMapSqlParameterSource(entity));
        return findById(entity.getId());
    }

    @Override
    public <S extends Receipt> List<Receipt> updateAll(Collection<S> entities) {
        getJdbcTemplate().batchUpdate(SQL_UPDATE_BY_ID, createBatchMapSqlParameterSource(entities));
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        return findAllById(ids);
    }

    @Override
    public Receipt add(Receipt entity) {
        var keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(SQL_INSERT, createMapSqlParameterSource(entity), keyHolder, new String[]{"id"});
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public <S extends Receipt> List<Receipt> addAll(Collection<S> entities) {
        return entities.stream().map(this::add).collect(Collectors.toList());
    }

    private static MapSqlParameterSource[] createBatchMapSqlParameterSource(Collection<? extends Receipt> entities) {
        return entities.stream().map(ReceiptDao::createMapSqlParameterSource)
                .toArray(MapSqlParameterSource[]::new);
    }

    private static MapSqlParameterSource createMapSqlParameterSource(Receipt entity) {
        return new MapSqlParameterSource()
                .addValue(Entities.Column.ID, entity.getId())
                .addValue(Entities.Column.NAME, entity.getName())
                .addValue(Entities.Column.DESCRIPTION, entity.getDescription())
                .addValue(Entities.Column.DATE, entity.getDate())
                .addValue(Entities.Column.CASHIER, entity.getCashier())
                .addValue(Entities.Column.ADDRESS, entity.getAddress())
                .addValue(Entities.Column.PHONE_NUMBER, entity.getPhoneNumber());
    }
}
