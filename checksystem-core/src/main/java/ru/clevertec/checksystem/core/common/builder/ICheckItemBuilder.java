package ru.clevertec.checksystem.core.common.builder;

import ru.clevertec.checksystem.core.common.IBuildable;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.discount.checkitem.CheckItemDiscount;

import java.util.Collection;

public interface ICheckItemBuilder extends IBuildable<CheckItem> {

    ICheckItemBuilder setId(Long id);

    ICheckItemBuilder setProduct(Product product);

    ICheckItemBuilder setQuantity(int quantity);

    ICheckItemBuilder setDiscounts(Collection<CheckItemDiscount> checkItemDiscounts);

    ICheckItemBuilder addDiscount(CheckItemDiscount checkItemDiscount);

    ICheckItemBuilder addDiscounts(Collection<CheckItemDiscount> checkItemDiscounts);
}
