package ru.clevertec.checksystem.core.common.discount;

import ru.clevertec.checksystem.core.entity.discount.Discount;

import java.math.BigDecimal;

public interface IDiscountable<T extends Discount<T>> extends IDiscountAggregable<T> {

    BigDecimal discountsAmount();
}
