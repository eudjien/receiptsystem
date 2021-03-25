package ru.clevertec.checksystem.webmvcjdbc.dao.discount.receiptItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;
import ru.clevertec.checksystem.webmvcjdbc.dao.Dao;
import ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receiptItem.SimpleConstantReceiptItemDiscountMapper;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SimpleConstantReceiptItemDiscountDao extends Dao<SimpleConstantReceiptItemDiscount, Long> {

    private final static String SQL_FIND_BY_ID = "" +
            "SELECT parent.id, parent.description, child.constant, parent.dependent_discount_id " +
            "FROM " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + " child " +
            "JOIN " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " parent ON child.id = parent.id " +
            "WHERE parent.id = :id ";

    private final static String SQL_FIND_ALL = "" +
            "SELECT * " +
            "FROM " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + " child " +
            "JOIN " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " parent ON child.id = parent.id ";

    private final static String SQL_FIND_ALL_BY_ID = "" +
            "SELECT * " +
            "FROM " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + " child " +
            "JOIN " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " parent ON child.id = parent.id " +
            "WHERE parent.id IN (:ids)";

    private final static String SQL_DELETE_ALL = "" +
            "DELETE FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " " +
            "WHERE type = " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT;

    private final static String SQL_DELETE_BY_ID = "" +
            "DELETE FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " " +
            "WHERE type = " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + " AND id = :id";

    private final static String SQL_DELETE_ALL_BY_ID = "" +
            "DELETE FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " " +
            "WHERE type = " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + " AND id IN(:ids)";

    private final static String SQL_UPDATE_PARENT_BY_ID = "" +
            "UPDATE " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " " +
            "SET description = :description " +
            "WHERE id = :id";

    private final static String SQL_UPDATE_CHILD_BY_ID = "" +
            "UPDATE " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + " " +
            "SET constant = :constant " +
            "WHERE id = :id";

    private final static String SQL_INSERT_PARENT = "" +
            "INSERT INTO " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + "(id, description, type) " +
            "VALUES(:id, :description, " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + ")";

    private final static String SQL_INSERT_CHILD = "" +
            "INSERT INTO " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT + "(id, constant) " +
            "VALUES(:id, :constant)";

    private final SimpleConstantReceiptItemDiscountMapper rowMapper;

    @Autowired
    public SimpleConstantReceiptItemDiscountDao(DataSource dataSource, SimpleConstantReceiptItemDiscountMapper rowMapper) {
        super(new NamedParameterJdbcTemplate(dataSource));
        this.rowMapper = rowMapper;
    }

    @Override
    public List<SimpleConstantReceiptItemDiscount> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public List<SimpleConstantReceiptItemDiscount> findAllById(Collection<Long> ids) {
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().query(SQL_FIND_ALL_BY_ID, parameters, rowMapper);
    }

    @Override
    public SimpleConstantReceiptItemDiscount findById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, parameters, rowMapper);
    }

    @Override
    public int deleteById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public int delete(SimpleConstantReceiptItemDiscount entity) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, entity.getId());
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public <S extends SimpleConstantReceiptItemDiscount> int deleteAll(Collection<S> entities) {
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().update(SQL_DELETE_ALL_BY_ID, parameters);
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(SQL_DELETE_ALL, new MapSqlParameterSource());
    }

    @Override
    public SimpleConstantReceiptItemDiscount update(SimpleConstantReceiptItemDiscount entity) {
        getJdbcTemplate().update(SQL_UPDATE_PARENT_BY_ID, createMapSqlParameterSourceParent(entity));
        getJdbcTemplate().update(SQL_UPDATE_CHILD_BY_ID, createMapSqlParameterSourceChild(entity));
        return findById(entity.getId());
    }

    @Override
    public <S extends SimpleConstantReceiptItemDiscount> List<SimpleConstantReceiptItemDiscount> updateAll(Collection<S> entities) {
        getJdbcTemplate().batchUpdate(SQL_UPDATE_PARENT_BY_ID, createBatchMapSqlParameterSourceParent(entities));
        getJdbcTemplate().batchUpdate(SQL_UPDATE_CHILD_BY_ID, createBatchMapSqlParameterSourceChild(entities));
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        return findAllById(ids);
    }

    @Override
    public SimpleConstantReceiptItemDiscount add(SimpleConstantReceiptItemDiscount entity) {
        var keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(SQL_INSERT_PARENT, createMapSqlParameterSourceParent(entity), keyHolder, new String[]{Entities.Column.ID});
        getJdbcTemplate().update(SQL_INSERT_CHILD, createMapSqlParameterSourceChild(entity), keyHolder, new String[]{Entities.Column.ID});
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public <S extends SimpleConstantReceiptItemDiscount> List<SimpleConstantReceiptItemDiscount> addAll(Collection<S> entities) {
        return entities.stream().map(this::add).collect(Collectors.toList());
    }

    private static MapSqlParameterSource[] createBatchMapSqlParameterSourceParent(Collection<? extends ReceiptItemDiscount> entities) {
        return entities.stream().map(SimpleConstantReceiptItemDiscountDao::createMapSqlParameterSourceParent)
                .toArray(MapSqlParameterSource[]::new);
    }

    private static MapSqlParameterSource[] createBatchMapSqlParameterSourceChild(Collection<? extends SimpleConstantReceiptItemDiscount> entities) {
        return entities.stream().map(SimpleConstantReceiptItemDiscountDao::createMapSqlParameterSourceChild)
                .toArray(MapSqlParameterSource[]::new);
    }

    private static MapSqlParameterSource createMapSqlParameterSourceParent(ReceiptItemDiscount entity) {
        return new MapSqlParameterSource()
                .addValue(Entities.Column.ID, entity.getId())
                .addValue(Entities.Column.DESCRIPTION, entity.getDescription())
                .addValue(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID, entity.getDependentDiscountId());
    }

    private static MapSqlParameterSource createMapSqlParameterSourceChild(SimpleConstantReceiptItemDiscount entity) {
        return new MapSqlParameterSource()
                .addValue(Entities.Column.ID, entity.getId())
                .addValue(Entities.Column.CONSTANT, entity.getConstant());
    }
}
