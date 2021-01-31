package ru.clevertec.checksystem.core.common.builder;

import ru.clevertec.checksystem.core.common.IBuildable;
import ru.clevertec.checksystem.core.entity.Product;
import ru.clevertec.checksystem.core.entity.check.CheckItem;
import ru.clevertec.checksystem.core.entity.discount.check.item.CheckItemDiscount;

public interface ICheckItemBuilder extends IBuildable<CheckItem> {

    ICheckItemBuilder setId(int id);

    ICheckItemBuilder setProduct(Product product);

    ICheckItemBuilder setQuantity(int quantity);

    ICheckItemBuilder setDiscounts(CheckItemDiscount... checkItemDiscounts);
}
