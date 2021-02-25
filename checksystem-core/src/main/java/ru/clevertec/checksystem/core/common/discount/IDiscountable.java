package ru.clevertec.checksystem.core.common.discount;

import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;

import java.math.BigDecimal;

public interface IDiscountable<T extends AbstractDiscount<T>> extends IDiscountAggregable<T> {

    BigDecimal discountsAmount();
}
