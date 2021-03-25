package ru.clevertec.checksystem.webmvcjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.webmvcjdbc.mapper.ReceiptItemMapper;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReceiptItemDao extends Dao<ReceiptItem, Long> {

    private final static String SQL_FIND_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPT_ITEMS + " WHERE id = :id";
    private final static String SQL_FIND_ALL = "SELECT * FROM " + Entities.Table.RECEIPT_ITEMS;
    private final static String SQL_FIND_ALL_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPT_ITEMS + " WHERE id IN (:ids)";

    private final static String SQL_DELETE_ALL = "DELETE FROM " + Entities.Table.RECEIPT_ITEMS;
    private final static String SQL_DELETE_BY_ID = "DELETE FROM " + Entities.Table.RECEIPT_ITEMS + " WHERE id = :id";
    private final static String SQL_DELETE_ALL_BY_ID = "DELETE FROM " + Entities.Table.RECEIPT_ITEMS + " WHERE id IN(:ids)";

    private final static String SQL_UPDATE_BY_ID = "" +
            "UPDATE " + Entities.Table.RECEIPT_ITEMS + " " +
            "SET product_id = :product_id, quantity = :quantity, receipt_id = :receipt_id " +
            "WHERE id = :id";

    private final static String SQL_INSERT = "" +
            "INSERT INTO " + Entities.Table.RECEIPT_ITEMS + "(id, product_id, quantity, receipt_id) " +
            "VALUES(:id, :product_id, :quantity, :receipt_id)";

    private final static String SQL_RECEIPT_ITEMS_BY_RECEIPT_ID = "" +
            "SELECT * FROM " + Entities.Table.RECEIPT_ITEMS + " " +
            "WHERE receipt_id = :id";

    private final ReceiptItemMapper rowMapper;

    @Autowired
    public ReceiptItemDao(DataSource dataSource, ReceiptItemMapper rowMapper) {
        super(new NamedParameterJdbcTemplate(dataSource));
        this.rowMapper = rowMapper;
    }

    @Override
    public List<ReceiptItem> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public List<ReceiptItem> findAllById(Collection<Long> ids) {
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().query(SQL_FIND_ALL_BY_ID, parameters, rowMapper);
    }

    @Override
    public ReceiptItem findById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, parameters, rowMapper);
    }

    @Override
    public int deleteById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public int delete(ReceiptItem entity) {
        var parameters = new MapSqlParameterSource().addValue("id", entity.getId());
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public <S extends ReceiptItem> int deleteAll(Collection<S> entities) {
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().update(SQL_DELETE_ALL_BY_ID, parameters);
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(SQL_DELETE_ALL, new MapSqlParameterSource());
    }

    @Override
    public ReceiptItem update(ReceiptItem entity) {
        getJdbcTemplate().update(SQL_UPDATE_BY_ID, createMapSqlParameterSource(entity));
        return findById(entity.getId());
    }

    @Override
    public <S extends ReceiptItem> List<ReceiptItem> updateAll(Collection<S> entities) {
        getJdbcTemplate().batchUpdate(SQL_UPDATE_BY_ID, createBatchMapSqlParameterSource(entities));
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        return findAllById(ids);
    }

    @Override
    public ReceiptItem add(ReceiptItem entity) {
        var keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(SQL_INSERT, createMapSqlParameterSource(entity), keyHolder, new String[]{Entities.Column.ID});
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public <S extends ReceiptItem> List<ReceiptItem> addAll(Collection<S> entities) {
        return entities.stream().map(this::add).collect(Collectors.toList());
    }

    public List<ReceiptItem> findAllByReceiptId(Long receiptId) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, receiptId);
        return getJdbcTemplate().query(SQL_RECEIPT_ITEMS_BY_RECEIPT_ID, parameters, rowMapper);
    }

    private static MapSqlParameterSource[] createBatchMapSqlParameterSource(Collection<? extends ReceiptItem> entities) {
        return entities.stream().map(ReceiptItemDao::createMapSqlParameterSource)
                .toArray(MapSqlParameterSource[]::new);
    }

    private static MapSqlParameterSource createMapSqlParameterSource(ReceiptItem entity) {
        return new MapSqlParameterSource()
                .addValue(Entities.Column.ID, entity.getId())
                .addValue(Entities.Column.QUANTITY, entity.getQuantity())
                .addValue(Entities.JoinColumn.PRODUCT_ID, entity.getProductId())
                .addValue(Entities.JoinColumn.RECEIPT_ID, entity.getReceiptId());
    }
}
