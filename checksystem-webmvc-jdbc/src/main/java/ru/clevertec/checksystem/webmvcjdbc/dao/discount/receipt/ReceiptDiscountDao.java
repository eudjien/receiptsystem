package ru.clevertec.checksystem.webmvcjdbc.dao.discount.receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimpleConstantReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.SimplePercentageReceiptDiscount;
import ru.clevertec.checksystem.webmvcjdbc.dao.Dao;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReceiptDiscountDao extends Dao<ReceiptDiscount, Long> {

    private final static String SQL_FIND_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPT_DISCOUNTS + " WHERE id = :id";
    private final static String SQL_FIND_ALL = "SELECT * FROM " + Entities.Table.RECEIPT_DISCOUNTS;
    private final static String SQL_FIND_ALL_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPT_DISCOUNTS + " WHERE id IN (:ids)";

    private final static String SQL_DELETE_ALL = "DELETE FROM " + Entities.Table.RECEIPT_DISCOUNTS;
    private final static String SQL_DELETE_BY_ID = "DELETE FROM  " + Entities.Table.RECEIPT_DISCOUNTS + " WHERE id = :id";
    private final static String SQL_DELETE_ALL_BY_ID = "DELETE FROM  " + Entities.Table.RECEIPT_DISCOUNTS + " WHERE id IN(:ids)";

    private final static String SQL_DISCOUNT_IDS_BY_RECEIPT_ID = "" +
            "SELECT receipt_discount_id id " +
            "FROM " + Entities.Table.RECEIPT__RECEIPT_DISCOUNT + " " +
            "WHERE receipt_id = :id";

    private final RowMapper<Map.Entry<Long, String>> idTypeRowMapper;

    private final SimpleConstantReceiptDiscountDao simpleConstantReceiptDiscountDao;
    private final SimplePercentageReceiptDiscountDao simplePercentageReceiptDiscountDao;

    @Autowired
    public ReceiptDiscountDao(DataSource dataSource,
                              SimpleConstantReceiptDiscountDao simpleConstantReceiptDiscountDao,
                              SimplePercentageReceiptDiscountDao simplePercentageReceiptDiscountDao) {
        super(new NamedParameterJdbcTemplate(dataSource));
        this.simpleConstantReceiptDiscountDao = simpleConstantReceiptDiscountDao;
        this.simplePercentageReceiptDiscountDao = simplePercentageReceiptDiscountDao;
        this.idTypeRowMapper = (rs, rowNum) -> new AbstractMap.SimpleEntry<>(
                rs.getLong(Entities.Column.ID), rs.getString(Entities.DiscriminatorNames.RECEIPT_DISCOUNT));
    }

    @Override
    public List<ReceiptDiscount> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL, idTypeRowMapper).stream()
                .map(this::findChild).collect(Collectors.toList());
    }

    @Override
    public List<ReceiptDiscount> findAllById(Collection<Long> ids) {
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().query(SQL_FIND_ALL_BY_ID, parameters, idTypeRowMapper).stream()
                .map(this::findChild).collect(Collectors.toList());
    }

    @Override
    public ReceiptDiscount findById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        var parent = getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, parameters, idTypeRowMapper);
        return findChild(Objects.requireNonNull(parent));
    }

    @Override
    public int deleteById(Long id) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, id);
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public int delete(ReceiptDiscount entity) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, entity.getId());
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public <S extends ReceiptDiscount> int deleteAll(Collection<S> entities) {
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().update(SQL_DELETE_ALL_BY_ID, parameters);
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(SQL_DELETE_ALL, new MapSqlParameterSource());
    }

    @Override
    public ReceiptDiscount update(ReceiptDiscount entity) {
        updateOne(entity);
        return findById(entity.getId());
    }

    @Override
    public <S extends ReceiptDiscount> List<ReceiptDiscount> updateAll(Collection<S> entities) {
        entities.forEach(this::updateOne);
        return findAllById(entities.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Override
    public ReceiptDiscount add(ReceiptDiscount entity) {
        if (entity instanceof SimpleConstantReceiptDiscount)
            return simpleConstantReceiptDiscountDao.add((SimpleConstantReceiptDiscount) entity);
        else if (entity instanceof SimplePercentageReceiptDiscount)
            return simplePercentageReceiptDiscountDao.add((SimplePercentageReceiptDiscount) entity);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public <S extends ReceiptDiscount> List<ReceiptDiscount> addAll(Collection<S> entities) {
        return entities.stream().map(this::add).collect(Collectors.toList());
    }

    public List<ReceiptDiscount> findAllByReceiptId(Long receiptId) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, receiptId);
        var discountIds = getJdbcTemplate().query(SQL_DISCOUNT_IDS_BY_RECEIPT_ID, parameters,
                (r, i) -> r.getLong(Entities.Column.ID));
        return findAllById(discountIds);
    }

    private ReceiptDiscount findChild(Map.Entry<Long, String> idTypeEntry) {
        if (idTypeEntry.getValue().equals(Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_DISCOUNT))
            return simpleConstantReceiptDiscountDao.findById(idTypeEntry.getKey());
        else if (idTypeEntry.getValue().equals(Entities.DiscriminatorValues.SIMPLE_PERCENTAGE_RECEIPT_DISCOUNT))
            return simplePercentageReceiptDiscountDao.findById(idTypeEntry.getKey());
        else
            throw new IllegalArgumentException();
    }

    private void updateOne(ReceiptDiscount entity) {
        if (entity instanceof SimpleConstantReceiptDiscount)
            simpleConstantReceiptDiscountDao.update((SimpleConstantReceiptDiscount) entity);
        else if (entity instanceof SimplePercentageReceiptDiscount)
            simplePercentageReceiptDiscountDao.update((SimplePercentageReceiptDiscount) entity);
        else
            throw new IllegalArgumentException();
    }
}
