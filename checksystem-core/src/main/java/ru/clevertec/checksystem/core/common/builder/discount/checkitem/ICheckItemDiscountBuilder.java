package ru.clevertec.checksystem.core.common.builder.discount.checkitem;

import ru.clevertec.checksystem.core.common.builder.discount.IDiscountBuilder;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;

import java.util.Collection;

public interface ICheckItemDiscountBuilder<T extends ICheckItemDiscountBuilder<T>>
        extends IDiscountBuilder<CheckItemDiscount, T> {

    T setCheckItems(Collection<CheckItem> checkItems);
}
