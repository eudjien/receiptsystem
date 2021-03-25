package ru.clevertec.checksystem.webmvcjdbc.dao.discount.receiptItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.annotation.execution.AroundExecutionLog;
import ru.clevertec.checksystem.core.constant.Entities;
import ru.clevertec.checksystem.core.entity.BaseEntity;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimpleConstantReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.SimplePercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;
import ru.clevertec.checksystem.webmvcjdbc.dao.Dao;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@AroundExecutionLog
@Component
public class ReceiptItemDiscountDao extends Dao<ReceiptItemDiscount, Long> {

    private final static String SQL_FIND_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " WHERE id = :id";
    private final static String SQL_FIND_ALL = "SELECT * FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS;
    private final static String SQL_FIND_ALL_BY_ID = "SELECT * FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " WHERE id IN (:ids)";

    private final static String SQL_DELETE_ALL = "DELETE FROM " + Entities.Table.RECEIPT_ITEM_DISCOUNTS;
    private final static String SQL_DELETE_BY_ID = "DELETE FROM  " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " WHERE id = :id";
    private final static String SQL_DELETE_ALL_BY_ID = "DELETE FROM  " + Entities.Table.RECEIPT_ITEM_DISCOUNTS + " WHERE id IN (:ids)";

    private final static String SQL_DISCOUNT_IDS_BY_RECEIPT_ITEM_ID = "" +
            "SELECT receipt_item_discount_id id " +
            "FROM " + Entities.Table.RECEIPT_ITEM__RECEIPT_ITEM_DISCOUNT + " " +
            "WHERE receipt_item_id = :id";

    private final RowMapper<Map.Entry<Long, String>> idTypeRowMapper;

    private final SimpleConstantReceiptItemDiscountDao simpleConstantReceiptItemDiscountDao;
    private final SimplePercentageReceiptItemDiscountDao simplePercentageReceiptItemDiscountDao;
    private final ThresholdPercentageReceiptItemDiscountDao thresholdPercentageReceiptItemDiscountDao;

    @Autowired
    public ReceiptItemDiscountDao(DataSource dataSource,
                                  SimpleConstantReceiptItemDiscountDao simpleConstantReceiptItemDiscountDao,
                                  SimplePercentageReceiptItemDiscountDao simplePercentageReceiptItemDiscountDao,
                                  ThresholdPercentageReceiptItemDiscountDao thresholdPercentageReceiptItemDiscountDao) {
        super(new NamedParameterJdbcTemplate(dataSource));
        this.simpleConstantReceiptItemDiscountDao = simpleConstantReceiptItemDiscountDao;
        this.simplePercentageReceiptItemDiscountDao = simplePercentageReceiptItemDiscountDao;
        this.thresholdPercentageReceiptItemDiscountDao = thresholdPercentageReceiptItemDiscountDao;
        this.idTypeRowMapper = (rs, rowNum) -> new AbstractMap.SimpleEntry<>(
                rs.getLong(Entities.Column.ID), rs.getString(Entities.DiscriminatorNames.RECEIPT_ITEM_DISCOUNT));
    }

    @Override
    public List<ReceiptItemDiscount> findAll() {
        return getJdbcTemplate().query(SQL_FIND_ALL, idTypeRowMapper).stream()
                .map(this::findChild).collect(Collectors.toList());
    }

    @Override
    public List<ReceiptItemDiscount> findAllById(Collection<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().query(SQL_FIND_ALL_BY_ID, parameters, idTypeRowMapper).stream()
                .map(this::findChild).collect(Collectors.toList());
    }

    @Override
    public ReceiptItemDiscount findById(Long id) {
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
    public int delete(ReceiptItemDiscount entity) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, entity.getId());
        return getJdbcTemplate().update(SQL_DELETE_BY_ID, parameters);
    }

    @Override
    public <S extends ReceiptItemDiscount> int deleteAll(Collection<S> entities) {
        var ids = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        var parameters = new MapSqlParameterSource().addValue("ids", ids);
        return getJdbcTemplate().update(SQL_DELETE_ALL_BY_ID, parameters);
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(SQL_DELETE_ALL, new MapSqlParameterSource());
    }

    @Override
    public ReceiptItemDiscount update(ReceiptItemDiscount entity) {
        updateOne(entity);
        return findById(entity.getId());
    }

    @Override
    public <S extends ReceiptItemDiscount> List<ReceiptItemDiscount> updateAll(Collection<S> entities) {
        entities.forEach(this::updateOne);
        return findAllById(entities.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Override
    public ReceiptItemDiscount add(ReceiptItemDiscount entity) {
        return addOne(entity);
    }

    @Override
    public <S extends ReceiptItemDiscount> List<ReceiptItemDiscount> addAll(Collection<S> entities) {
        return entities.stream().map(this::addOne).collect(Collectors.toList());
    }

    public List<ReceiptItemDiscount> findAllByReceiptItemId(Long receiptItemId) {
        var parameters = new MapSqlParameterSource().addValue(Entities.Column.ID, receiptItemId);
        var discountIds = getJdbcTemplate().query(SQL_DISCOUNT_IDS_BY_RECEIPT_ITEM_ID, parameters,
                (r, i) -> r.getLong(Entities.Column.ID));
        return findAllById(discountIds);
    }

    private ReceiptItemDiscount findChild(Map.Entry<Long, String> idTypeEntry) {
        return switch (idTypeEntry.getValue()) {
            case Entities.DiscriminatorValues.SIMPLE_CONSTANT_RECEIPT_ITEM_DISCOUNT -> simpleConstantReceiptItemDiscountDao.findById(idTypeEntry.getKey());
            case Entities.DiscriminatorValues.SIMPLE_PERCENTAGE_RECEIPT_ITEM_DISCOUNT -> simplePercentageReceiptItemDiscountDao.findById(idTypeEntry.getKey());
            case Entities.DiscriminatorValues.THRESHOLD_PERCENTAGE_RECEIPT_ITEM_DISCOUNT -> thresholdPercentageReceiptItemDiscountDao.findById(idTypeEntry.getKey());
            default -> throw new IllegalArgumentException();
        };
    }

    private void updateOne(ReceiptItemDiscount entity) {
        if (entity instanceof SimpleConstantReceiptItemDiscount)
            simpleConstantReceiptItemDiscountDao.update((SimpleConstantReceiptItemDiscount) entity);
        else if (entity instanceof SimplePercentageReceiptItemDiscount)
            simplePercentageReceiptItemDiscountDao.update((SimplePercentageReceiptItemDiscount) entity);
        else if (entity instanceof ThresholdPercentageReceiptItemDiscount)
            thresholdPercentageReceiptItemDiscountDao.update((ThresholdPercentageReceiptItemDiscount) entity);
        else
            throw new IllegalArgumentException();
    }

    private ReceiptItemDiscount addOne(ReceiptItemDiscount entity) {
        if (entity instanceof SimpleConstantReceiptItemDiscount)
            return simpleConstantReceiptItemDiscountDao.add((SimpleConstantReceiptItemDiscount) entity);
        else if (entity instanceof SimplePercentageReceiptItemDiscount)
            return simplePercentageReceiptItemDiscountDao.add((SimplePercentageReceiptItemDiscount) entity);
        else if (entity instanceof ThresholdPercentageReceiptItemDiscount)
            return thresholdPercentageReceiptItemDiscountDao.add((ThresholdPercentageReceiptItemDiscount) entity);
        else
            throw new IllegalArgumentException();
    }
}
