package ru.clevertec.checksystem.webmvcjdbc.dao.discount.receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;
import ru.clevertec.checksystem.webmvcjdbc.dao.Dao;
import ru.clevertec.checksystem.webmvcjdbc.mapper.discount.receipt.SimpleConstantReceiptDiscountMapper;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SimpleConstantReceiptDiscountDao extends Dao<SimpleConstantReceiptDiscount, Long> {

    private final static String SQL_FIND_BY_ID = "" +
            "SELECT parent.id, parent.description, child.constant, parent.dependent_discount_id " +
            "FROM " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + " child " +
            "JOIN " + Entities.Table.RECEIPT_DISCOUNTS + " parent ON child.id = parent.id " +
            "WHERE parent.id = :id ";

    private final static String SQL_FIND_ALL = "" +
            "SELECT * " +
            "FROM " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + " child " +
            "JOIN " + Entities.Table.RECEIPT_DISCOUNTS + " parent ON child.id = parent.id ";

    private final static String SQL_FIND_ALL_BY_ID = "" +
            "SELECT * " +
            "FROM " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + " child " +
            "JOIN " + Entities.Table.RECEIPT_DISCOUNTS + " parent ON child.id = parent.id " +
            "WHERE parent.id IN (:ids)";

    private final static String SQL_DELETE_ALL = "" +
            "DELETE FROM " + Entities.Table.RECEIPT_DISCOUNTS + " " +
            "WHERE type = " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT;

    private final static String SQL_DELETE_BY_ID = "" +
            "DELETE FROM " + Entities.Table.RECEIPT_DISCOUNTS + " " +
            "WHERE type = " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + " AND id = :id";

    private final static String SQL_DELETE_ALL_BY_ID = "" +
            "DELETE FROM " + Entities.Table.RECEIPT_DISCOUNTS + " " +
            "WHERE type = " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + " AND id IN(:ids)";

    private final static String SQL_UPDATE_PARENT_BY_ID = "" +
            "UPDATE " + Entities.Table.RECEIPT_DISCOUNTS + " " +
            "SET description = :description " +
            "WHERE id = :id";

    private final static String SQL_UPDATE_CHILD_BY_ID = "" +
            "UPDATE " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + " " +
            "SET constant = :constant " +
            "WHERE id = :id";

    private final static String SQL_INSERT_PARENT = "" +
            "INSERT INTO " + Entities.Table.RECEIPT_DISCOUNTS + "(id, description, type) " +
            "VALUES(:id, :description, " + Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + ")";

    private final static String SQL_INSERT_CHILD = "" +
            "INSERT INTO " + Entities.Table.SIMPLE_CONSTANT_RECEIPT_DISCOUNT + "(id, constant) " +
            "VALUES(:id, :constant)";

    private final SimpleConstantReceiptDiscountMapper rowMapper;

    @Autowired
    public SimpleConstantReceiptDiscountDao(DataSource dataSource, SimpleConstantReceiptDiscountMapper rowMapper) {
        super(new NamedParameterJdbcTemplate(dataSource));
        this.rowMapper = rowMapper;
    }

    @Override
    public List<SimpleConstantReceiptDiscount> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public List<SimpleConstantReceiptDiscount> findAllById(Collection<Long> ids) {
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().query(SQL_FIND_ALL_BY_ID, parameters, rowMapper);
    }

    @Override
    public SimpleConstantReceiptDiscount findById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, parameters, rowMapper);
    }

    @Override
    public int deleteById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public int delete(SimpleConstantReceiptDiscount entity) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, entity.getId());
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public <S extends SimpleConstantReceiptDiscount> int deleteAll(Collection<S> entities) {
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().update(SQL_DELETE_ALL_BY_ID, parameters);
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(SQL_DELETE_ALL, new MapSqlParameterSource());
    }

    @Override
    public SimpleConstantReceiptDiscount update(SimpleConstantReceiptDiscount entity) {
        getJdbcTemplate().update(SQL_UPDATE_PARENT_BY_ID, createMapSqlParameterSourceParent(entity));
        getJdbcTemplate().update(SQL_UPDATE_CHILD_BY_ID, createMapSqlParameterSourceChild(entity));
        return findById(entity.getId());
    }

    @Override
    public <S extends SimpleConstantReceiptDiscount> List<SimpleConstantReceiptDiscount> updateAll(Collection<S> entities) {
        getJdbcTemplate().batchUpdate(SQL_UPDATE_PARENT_BY_ID, createBatchMapSqlParameterSourceParent(entities));
        getJdbcTemplate().batchUpdate(SQL_UPDATE_CHILD_BY_ID, createBatchMapSqlParameterSourceChild(entities));
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        return findAllById(ids);
    }

    @Override
    public SimpleConstantReceiptDiscount add(SimpleConstantReceiptDiscount entity) {
        var keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(SQL_INSERT_PARENT, createMapSqlParameterSourceParent(entity), keyHolder, new String[]{Entities.Column.ID});
        getJdbcTemplate().update(SQL_INSERT_CHILD, createMapSqlParameterSourceChild(entity), keyHolder, new String[]{Entities.Column.ID});
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public <S extends SimpleConstantReceiptDiscount> List<SimpleConstantReceiptDiscount> addAll(Collection<S> entities) {
        return entities.stream().map(this::add).collect(Collectors.toList());
    }

    private static MapSqlParameterSource[] createBatchMapSqlParameterSourceParent(Collection<? extends ReceiptDiscount> entities) {
        return entities.stream().map(SimpleConstantReceiptDiscountDao::createMapSqlParameterSourceParent)
                .toArray(MapSqlParameterSource[]::new);
    }

    private static MapSqlParameterSource[] createBatchMapSqlParameterSourceChild(Collection<? extends SimpleConstantReceiptDiscount> entities) {
        return entities.stream().map(SimpleConstantReceiptDiscountDao::createMapSqlParameterSourceChild)
                .toArray(MapSqlParameterSource[]::new);
    }

    private static MapSqlParameterSource createMapSqlParameterSourceParent(ReceiptDiscount entity) {
        return new MapSqlParameterSource()
                .addValue(Entities.Column.ID, entity.getId())
                .addValue(Entities.Column.DESCRIPTION, entity.getDescription())
                .addValue(Entities.JoinColumn.DEPENDENT_DISCOUNT_ID, entity.getDependentDiscountId());
    }

    private static MapSqlParameterSource createMapSqlParameterSourceChild(SimpleConstantReceiptDiscount entity) {
        return new MapSqlParameterSource()
                .addValue(Entities.Column.ID, entity.getId())
                .addValue(Entities.Column.CONSTANT, entity.getConstant());
    }
}
