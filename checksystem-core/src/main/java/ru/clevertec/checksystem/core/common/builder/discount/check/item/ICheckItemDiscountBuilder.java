package ru.clevertec.checksystem.core.common.builder.discount.check.item;

import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.common.builder.discount.IDiscountBuilder;
import ru.clevertec.checksystem.core.entity.discount.check.item.CheckItemDiscount;

public interface ICheckItemDiscountBuilder<T extends ICheckItemDiscountBuilder<T>>
        extends IDiscountBuilder<CheckItemDiscount, T> {

    T setCheckItem(CheckItem checkItem);
}
